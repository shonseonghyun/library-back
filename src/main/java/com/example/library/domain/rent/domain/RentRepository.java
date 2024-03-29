package com.example.library.domain.rent.domain;

import com.example.library.domain.rent.domain.dto.RentStatusResponseDto;

import java.util.List;

public interface RentRepository {
    RentManager save(RentManager rentManager); //case1) RentManager만 저장 case2) RentManager,RentHistory 모두 저장
    RentManager findRentManagerByUserNo(Long userNo); //RentManager get
    RentManager findRentManagerWithRentedBookHistory(Long userNo, Long book); //RentManager + history on rent
    List<RentStatusResponseDto.Response> findUserRentStatus(Long userNo);
}
