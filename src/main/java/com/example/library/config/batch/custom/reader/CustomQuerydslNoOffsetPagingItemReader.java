package com.example.library.config.batch.custom.reader;

import com.example.library.domain.rent.domain.RentRepository;
import com.example.library.domain.rent.enums.RentState;
import com.example.library.domain.rent.infrastructure.entity.RentHistoryEntity;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

@RequiredArgsConstructor
public class CustomQuerydslNoOffsetPagingItemReader<T> extends CustomQuerydslPagingItemReader<T> {
    private Long cachedNo; //조회된 페이지의 마지막 ID 캐시
    private String nowDt;

    public CustomQuerydslNoOffsetPagingItemReader(EntityManagerFactory entityManagerFactory, String nowDt, int pageSize, Function<JPAQueryFactory, JPAQuery<T>> queryFunction){
        this.nowDt = nowDt;
        super.queryFunction = queryFunction;
        super.entityManagerFactory = entityManagerFactory;
        setPageSize(pageSize);
    }

    private void setCachedNo(Long cachedNo){
        this.cachedNo = cachedNo;
    }

    @Override
    protected JPAQuery<T> createQuery() {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<T> query = queryFunction.apply(jpaQueryFactory);
        query.where()
        return query;
    }



    @Override
    protected void doReadPage() {
//        //연체된 도서히스토리 조회 리스트
//        List<RentHistoryEntity> overdueRentHistoryList = rentRepository.getRentHistoryEntityByRentState(RentState.ON_RENT,cachedNo,nowDt,getPageSize());
//
//        if (results == null) {
//            results = new CopyOnWriteArrayList<>();
//        }
//        else {
//            results.clear();
//        }
//
//        if(!overdueRentHistoryList.isEmpty()){ //조회 대상이 존재한다면
//            //조회된 페이지의 마지막 ID 캐시 진행
//            int lastIdx = overdueRentHistoryList.size()-1;
//            setCachedNo(overdueRentHistoryList.get(lastIdx).getHistoryNo());
//
//            //addAll
//            results.addAll(overdueRentHistoryList);
//        }
        JPAQuery<T> query = createQuery()
                .limit(getPageSize());

        initResults();

        fetchQuery(query);

        //조회된 페이지의 마지막 No 캐시 진행
        if(!results.isEmpty()){
            int lastIdx = results.size()-1;
            setCachedNo(results.get(lastIdx).);
        }
//        setCachedNo(??);
    }

}
