package com.example.library.domain.user.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRentStatusResDto {
    private Long bookNo;
    private String bookName;
    private String rentDt;
    private String haveToReturnDt;
    private boolean extensionFlg;
}
