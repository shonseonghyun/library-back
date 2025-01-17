package com.example.library.config.batch.custom.reader;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

@RequiredArgsConstructor
public class CustomQuerydslPagingItemReader<T> extends AbstractPagingItemReader<T> {

    protected Function<JPAQueryFactory, JPAQuery<T>> queryFunction;
    protected EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;
    protected final Map<String, Object> jpaPropertyMap = new HashMap<>();
    protected boolean transacted = true;// default value

    public CustomQuerydslPagingItemReader(EntityManagerFactory entityManagerFactory, int pageSize, Function<JPAQueryFactory, JPAQuery<T>> queryFunction){
        this.entityManagerFactory = entityManagerFactory;
        this.queryFunction = queryFunction;
        setPageSize(pageSize);
    }

    public void setTransacted(boolean transacted) {
        this.transacted = transacted;
    }

    @Override
    protected void doOpen() throws Exception {
        super.doOpen();

        entityManager = entityManagerFactory.createEntityManager(jpaPropertyMap);
        if (entityManager == null) {
            throw new DataAccessResourceFailureException("Unable to obtain an EntityManager");
        }
    }

    @Override
    protected void doReadPage() {
        //커스터마이징
        JPAQuery<T> query = createQuery()
                .offset(getPage() * getPageSize())
                .limit(getPageSize());

        initResults();

        fetchQuery(query);
    }

    protected void fetchQuery(JPAQuery<T> query) {
        if (!transacted) { //?
            List<T> queryResult = query.fetch();
            for (T entity : queryResult) {
                entityManager.detach(entity);
                results.add(entity);
            } // end if
        }
        else {
            results.addAll(query.fetch());
        } // end if
    }

    protected void initResults() {
        if (CollectionUtils.isEmpty(results)) {
            results = new CopyOnWriteArrayList<>();
        } else {
            results.clear();
        }
    }

    protected JPAQuery<T> createQuery() {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        return queryFunction.apply(jpaQueryFactory);
    }

    @Override
    protected void doClose() throws Exception {
        entityManager.close();
        super.doClose();
    }
}
