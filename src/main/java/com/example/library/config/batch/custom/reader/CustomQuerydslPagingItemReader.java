package com.example.library.config.batch.custom.reader;

import com.example.library.config.batch.custom.dto.OverdueClearUserDto;
import com.example.library.domain.rent.domain.RentRepository;
import com.example.library.domain.rent.infrastructure.entity.RentManagerEntity;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.database.AbstractPagingItemReader;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.example.library.domain.rent.infrastructure.entity.QRentHistoryEntity.rentHistoryEntity;
import static com.example.library.domain.rent.infrastructure.entity.QRentManagerEntity.rentManagerEntity;

@RequiredArgsConstructor
public class CustomQuerydslPagingItemReader extends AbstractPagingItemReader<OverdueClearUserDto> {

    private final RentRepository rentRepository;

    public CustomQuerydslPagingItemReader(RentRepository rentRepository, int pageSize){
        this.rentRepository = rentRepository;
        setPageSize(pageSize);
    }

    @Override
    protected void doReadPage() {
        //overdueList get
        List<Tuple> overdueUserList = rentRepository.getOverdueClearList(getPageSize());

        if (results == null) {
            results = new CopyOnWriteArrayList<>();
        }
        else {
            results.clear();
        }

        for(Tuple tuple: overdueUserList){
            if(!overdueUserList.isEmpty()){ //조회 대상이 존재한다면
                RentManagerEntity savedRentManagerEntity= tuple.get(rentManagerEntity);
                String returnDt= tuple.get(rentHistoryEntity.returnDt.max());
                results.add(new OverdueClearUserDto(savedRentManagerEntity,returnDt));
            }
        }
    }
}
