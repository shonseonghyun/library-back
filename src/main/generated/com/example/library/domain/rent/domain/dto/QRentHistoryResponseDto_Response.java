package com.example.library.domain.rent.domain.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.example.library.domain.rent.domain.dto.QRentHistoryResponseDto_Response is a Querydsl Projection type for Response
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QRentHistoryResponseDto_Response extends ConstructorExpression<RentHistoryResponseDto.Response> {

    private static final long serialVersionUID = -826398942L;

    public QRentHistoryResponseDto_Response(com.querydsl.core.types.Expression<Long> bookNo, com.querydsl.core.types.Expression<String> bookName, com.querydsl.core.types.Expression<String> rentDt, com.querydsl.core.types.Expression<String> haveReturnDt, com.querydsl.core.types.Expression<String> returnDt, com.querydsl.core.types.Expression<Boolean> extensionFlg, com.querydsl.core.types.Expression<com.example.library.domain.rent.enums.RentState> rentState) {
        super(RentHistoryResponseDto.Response.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, boolean.class, com.example.library.domain.rent.enums.RentState.class}, bookNo, bookName, rentDt, haveReturnDt, returnDt, extensionFlg, rentState);
    }

}

