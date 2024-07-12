package com.example.library.config.batch.custom.reader;

import com.example.library.domain.rent.domain.RentRepository;
import com.example.library.domain.rent.enums.RentState;
import com.example.library.domain.rent.infrastructure.entity.RentHistoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.database.AbstractPagingItemReader;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RequiredArgsConstructor
public class CustomQuerydslNoOffsetPagingItemReader extends AbstractPagingItemReader<RentHistoryEntity> {

    private final RentRepository rentRepository;


    private Long cachedNo; //조회된 페이지의 마지막 ID 캐시
    private String nowDt;

    public CustomQuerydslNoOffsetPagingItemReader(RentRepository rentRepository, String nowDt, int pageSize){
        this.rentRepository = rentRepository;
        this.nowDt = nowDt;
        setPageSize(pageSize);
    }

    @Override
    protected void doReadPage() {
        //연체된 도서히스토리 조회 리스트
        List<RentHistoryEntity> overdueRentHistoryList = rentRepository.getRentHistoryEntityByRentState(RentState.ON_RENT,cachedNo,nowDt,getPageSize());

        if (results == null) {
            results = new CopyOnWriteArrayList<>();
        }
        else {
            results.clear();
        }

        if(!overdueRentHistoryList.isEmpty()){ //조회 대상이 존재한다면
            //조회된 페이지의 마지막 ID 캐시 진행
            int lastIdx = overdueRentHistoryList.size()-1;
            cachedNo = overdueRentHistoryList.get(lastIdx).getHistoryNo();

            //addAll
            results.addAll(overdueRentHistoryList);
        }
    }
}
