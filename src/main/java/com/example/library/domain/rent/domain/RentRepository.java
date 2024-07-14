package com.example.library.domain.rent.domain;

import com.example.library.config.batch.custom.dto.OverdueClearUserDto;
import com.example.library.domain.rent.domain.dto.RentHistoryResponseDto;
import com.example.library.domain.rent.domain.dto.RentStatusResponseDto;
import com.example.library.domain.rent.enums.RentState;
import com.example.library.domain.rent.infrastructure.entity.RentHistoryEntity;

import java.util.List;

public interface RentRepository {
    RentManager save(RentManager rentManager); //case1) RentManager만 저장 case2) RentManager,RentHistory 모두 저장
    RentManager findRentManagerByUserNo(Long userNo); //RentManager get
    RentManager findRentManagerWithRentedBookHistory(Long userNo, Long book); //RentManager + history on rent
    List<RentStatusResponseDto.Response> findUserRentStatus(Long userNo);
    List<RentHistoryResponseDto.Response> findUserRentHistory(Long userNo);
    void deleteByUserNo(Long userNo);
    Integer findRentHistoryCountWithReturn(Long userNo, Long bookNo);
    //연체중인 도서를 모두 반환한 유저
//    List<OverdueClearUserDto> getOverdueClearList(int pageSize);

    //RentState 값을 통한 RentHistory 리스트 for Batch
//    List<RentHistoryEntity> getRentHistoryEntityByRentState(RentState rentState, Long lastHistoryNo,String nowDt,int pageSize);
}
