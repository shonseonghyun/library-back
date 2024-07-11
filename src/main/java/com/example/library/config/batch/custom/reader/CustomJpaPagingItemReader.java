package com.example.library.config.batch.custom.reader;

import com.example.library.domain.rent.domain.RentRepository;
import com.example.library.domain.rent.infrastructure.entity.RentManagerEntity;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.database.AbstractPagingItemReader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.example.library.domain.rent.infrastructure.entity.QRentHistoryEntity.rentHistoryEntity;
import static com.example.library.domain.rent.infrastructure.entity.QRentManagerEntity.rentManagerEntity;

@RequiredArgsConstructor
public class CustomJpaPagingItemReader extends AbstractPagingItemReader<RentManagerEntity> {

    private final RentRepository rentRepository;

    public CustomJpaPagingItemReader(RentRepository rentRepository,int pageSize){
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
            Long managerNo= tuple.get(rentManagerEntity.managerNo);
            String returnDt= tuple.get(rentHistoryEntity.returnDt.max());

            if(isSameLastReturnDateAfter7Days(returnDt)){
                RentManagerEntity rentManagerEntity = rentRepository.findRentManagerEntityByManagerNo(managerNo);
                results.add(rentManagerEntity);
            }
        }
    }

    private boolean isSameLastReturnDateAfter7Days(String returnDt) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        //금일날짜
        String nowDt = formatter.format(cal.getTime());

        //반납일자
        Date strToDt = null;
        try {
            strToDt = formatter.parse(returnDt);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        //반납일자로부터 +7일 날자 계산
        cal.setTime(strToDt);
        cal.add(Calendar.DAY_OF_MONTH,+7);
        Date after7Days = cal.getTime();

        //반납일자로부터 +7일 string형식
        String after7DaysStr = formatter.format(after7Days);

        return nowDt.equals(after7DaysStr);
    }
}
