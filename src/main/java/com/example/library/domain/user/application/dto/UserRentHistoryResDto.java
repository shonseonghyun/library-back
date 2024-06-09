package com.example.library.domain.user.application.dto;

import com.example.library.domain.rent.enums.RentState;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRentHistoryResDto {
    private Long bookNo;
    private String bookName;
    private String rentDt;
    private String haveToReturnDt;
    private String returnDt;
    private boolean extensionFlg;
    private RentState rentState;
}
