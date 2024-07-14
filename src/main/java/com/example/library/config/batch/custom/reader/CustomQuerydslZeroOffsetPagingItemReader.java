package com.example.library.config.batch.custom.reader;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;


@RequiredArgsConstructor
public class CustomQuerydslZeroOffsetPagingItemReader<T> extends CustomQuerydslPagingItemReader<T> {
    //반드시 desc로 orderBy진행
    public CustomQuerydslZeroOffsetPagingItemReader(EntityManagerFactory entityManagerFactory, int pageSize, Function<JPAQueryFactory, JPAQuery<T>> queryFunction){
        super.queryFunction = queryFunction;
        super.entityManagerFactory = entityManagerFactory;
        setPageSize(pageSize);
    }

    @Override
    protected void doReadPage() {
        //커스터마이징
        JPAQuery<T> query = createQuery()
                .offset(0)
                .limit(getPageSize());
                
        initResults();

        fetchQuery(query);
    }
}
