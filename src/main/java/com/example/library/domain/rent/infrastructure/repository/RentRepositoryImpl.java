package com.example.library.domain.rent.infrastructure.repository;

import com.example.library.domain.rent.domain.RentHistory;
import com.example.library.domain.rent.domain.RentManager;
import com.example.library.domain.rent.domain.RentRepository;
import com.example.library.domain.rent.domain.dto.QRentHistoryResponseDto_Response;
import com.example.library.domain.rent.domain.dto.QRentStatusResponseDto_Response;
import com.example.library.domain.rent.domain.dto.RentHistoryResponseDto;
import com.example.library.domain.rent.domain.dto.RentStatusResponseDto;
import com.example.library.domain.rent.enums.RentState;
import com.example.library.domain.rent.infrastructure.entity.QRentHistoryEntity;
import com.example.library.domain.rent.infrastructure.entity.RentHistoryEntity;
import com.example.library.domain.rent.infrastructure.entity.RentManagerEntity;
import com.example.library.exception.ErrorCode;
import com.example.library.exception.exceptions.BookOnRentException;
import com.example.library.exception.exceptions.RentManagerNotFoudException;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.library.domain.book.domain.QBookEntity.bookEntity;
import static com.example.library.domain.rent.infrastructure.entity.QRentHistoryEntity.rentHistoryEntity;
import static com.example.library.domain.rent.infrastructure.entity.QRentManagerEntity.rentManagerEntity;

@Repository
@RequiredArgsConstructor
public class RentRepositoryImpl implements RentRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final SpringDataJpaRentManagerRepository rentManagerRepository;
    private final SpringDataJpaRentHistoryRepository rentHistoryRepository;

    @Override
    public RentManager save(RentManager rentManager) {
        RentManagerEntity mangerEntity = rentManagerRepository.save(convert(rentManager));
        if(rentManager.getRentHistory()!=null){
            RentHistoryEntity historyEntity = rentHistoryRepository.save(convert(rentManager.getRentHistory()));
        }

        return convert(mangerEntity);
    }

    @Override
    public RentManager findRentManagerByUserNo(Long userNo) {
        RentManagerEntity mangerEntity = rentManagerRepository.findByUserNo(userNo)
                .orElseThrow(()->new RentManagerNotFoudException(ErrorCode.RENTMANAGER_USERNO_NOT_FOUND));
        RentManager rentManager = convert(mangerEntity);
//        //대여 중인 history 조회
//        List<RentHistoryEntity> historyEntityList = rentHistoryRepository.findByUserNoAndRentState(userNo,RentState.ON_RENT);
//
//        //존재한다면 rentManager도메인 필드에 세팅
//        if(historyEntityList.size()>0){
//            List<RentHistory> rentHistoryList = historyEntityList.stream().map(rentHistoryEntity->convert(rentHistoryEntity))
//                            .collect(Collectors.toList());
//            rentManager.setRentHistoryList(rentHistoryList);
//        }
        return rentManager;
    }

    @Override
    public RentManager findRentManagerWithRentedBookHistory(Long userNo, Long bookNo) {
        RentManagerEntity managerEntity = rentManagerRepository.findByUserNo(userNo)
                .orElseThrow(()->new RentManagerNotFoudException(ErrorCode.RENTMANAGER_USERNO_NOT_FOUND));
        RentHistoryEntity historyEntity = rentHistoryRepository.findByUserNoAndBookNoAndRentState(userNo,bookNo, RentState.ON_RENT)
                .orElseThrow(()->new BookOnRentException(ErrorCode.BOOK_NOT_FOUND_AMONG_BOOKS_ON_RENT));
        RentManager managerDomain = convert(managerEntity);
        RentHistory historyDomain = convert(historyEntity);

        managerDomain.setRentHistory(historyDomain);

        return managerDomain;
    }

    @Override
    public Integer findRentHistoryCountWithReturn(Long userNo, Long bookNo) {
        RentManagerEntity managerEntity = rentManagerRepository.findByUserNo(userNo)
                .orElseThrow(()->new RentManagerNotFoudException(ErrorCode.RENTMANAGER_USERNO_NOT_FOUND));

        return Math.toIntExact(jpaQueryFactory.select(rentHistoryEntity.count())
                .from(rentHistoryEntity)
                .where(rentHistoryEntity.userNo.eq(userNo)
                        .and(rentHistoryEntity.bookNo.eq(bookNo))
                        .and(rentHistoryEntity.rentState.notIn(RentState.ON_RENT))
                )
                .fetchFirst())
        ;
    }



    @Override
    public List<RentStatusResponseDto.Response> findUserRentStatus(Long userNo) {
        List<RentStatusResponseDto.Response> result = jpaQueryFactory.select(new QRentStatusResponseDto_Response(rentHistoryEntity.bookNo,bookEntity.bookName,rentHistoryEntity.rentDt,rentHistoryEntity.haveToReturnDt,rentHistoryEntity.extensionFlg))
                .from(rentHistoryEntity)
                .where(rentHistoryEntity.userNo.eq(userNo))
                .where(rentHistoryEntity.rentState.eq(RentState.ON_RENT))
                .innerJoin(bookEntity)
                .on(rentHistoryEntity.bookNo.eq(bookEntity.bookCode))
                .fetch()
                ;
        return result;
    }

    @Override
    public List<RentHistoryResponseDto.Response> findUserRentHistory(Long userNo) {
        List<RentHistoryResponseDto.Response> result =
                jpaQueryFactory.select(new QRentHistoryResponseDto_Response(rentHistoryEntity.historyNo,rentHistoryEntity.bookNo,bookEntity.bookName,rentHistoryEntity.rentDt,rentHistoryEntity.haveToReturnDt,rentHistoryEntity.returnDt,rentHistoryEntity.extensionFlg,rentHistoryEntity.rentState))
                        .from(rentHistoryEntity)
                        .where(rentHistoryEntity.userNo.eq(userNo))
                        .where(rentHistoryEntity.rentState.notIn(RentState.ON_RENT))
                        .innerJoin(bookEntity)
                        .on(rentHistoryEntity.bookNo.eq(bookEntity.bookCode))
                        .fetch()
                ;
        return result;
    }

    @Override
    public void deleteByUserNo(Long userNo) {
        rentManagerRepository.deleteByUserNo(userNo);
        rentHistoryRepository.deleteByUserNo(userNo);
    }

    @Override
    public List<Tuple> getOverdueClearList(int pageSize) {
        QRentHistoryEntity subRentHistory = new QRentHistoryEntity("subRentHistory");

        return jpaQueryFactory.select(rentManagerEntity,rentHistoryEntity.returnDt.max())
                .from(rentManagerEntity)
                .join(rentHistoryEntity)
                .on(rentManagerEntity.managerNo.eq(rentHistoryEntity.managerNo))
                .where(
                        rentManagerEntity.overdueFlg.eq(true)
                        .and(
                                JPAExpressions
                                        .select(subRentHistory).from(subRentHistory)
                                        .where(subRentHistory.rentState.eq(RentState.ON_OVERDUE),subRentHistory.managerNo.eq(rentManagerEntity.managerNo))
                                        .notExists()
                        )
                        .and(rentHistoryEntity.rentState.eq(RentState.OVERDUE_RETURN))
                )
                .groupBy(rentManagerEntity.managerNo)
                .orderBy(rentManagerEntity.managerNo.desc())
                .offset(0)
                .limit(pageSize)
                .fetch()
                ;
    }

    private RentManager convert(RentManagerEntity entity){
        return RentManager.by(entity);
    }

    private RentManagerEntity convert(RentManager domain){
        return RentManagerEntity.builder()
                .managerNo(domain.getManagerNo())
                .userNo(domain.getUserNo())
                .currentRentNumber(domain.getCurrentRentNumber())
                .overdueFlg(domain.isOverdue())
                .build()
                ;
    }

    private RentHistory convert(RentHistoryEntity rentHistoryEntity){
        return RentHistory.by(rentHistoryEntity);
    }

    private RentHistoryEntity convert(RentHistory domain){
        return RentHistoryEntity.builder()
                .historyNo(domain.getHistoryNo())
                .userNo(domain.getUserNo())
                .managerNo(domain.getManagerNo())
                .bookNo(domain.getBookNo())
                .rentDt(domain.getRentDt())
                .returnDt(domain.getReturnDt())
                .haveToReturnDt(domain.getHaveToReturnDt())
                .extensionFlg(domain.isExtensionFlg())
                .rentState(domain.getRentState())
                .build()
                ;

    }
}
