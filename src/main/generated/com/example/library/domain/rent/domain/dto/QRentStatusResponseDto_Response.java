package com.example.library.domain.rent.domain.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.example.library.domain.rent.domain.dto.QRentStatusResponseDto_Response is a Querydsl Projection type for Response
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QRentStatusResponseDto_Response extends ConstructorExpression<RentStatusResponseDto.Response> {

    private static final long serialVersionUID = 1931083750L;

    public QRentStatusResponseDto_Response(com.querydsl.core.types.Expression<Long> bookNo, com.querydsl.core.types.Expression<String> bookName, com.querydsl.core.types.Expression<String> rentDt, com.querydsl.core.types.Expression<String> haveToReturnDt, com.querydsl.core.types.Expression<Boolean> extensionFlg) {
        super(RentStatusResponseDto.Response.class, new Class<?>[]{long.class, String.class, String.class, String.class, boolean.class}, bookNo, bookName, rentDt, haveToReturnDt, extensionFlg);
    }

}

