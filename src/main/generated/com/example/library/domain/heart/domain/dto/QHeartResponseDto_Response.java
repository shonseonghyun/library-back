package com.example.library.domain.heart.domain.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.example.library.domain.heart.domain.dto.QHeartResponseDto_Response is a Querydsl Projection type for Response
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QHeartResponseDto_Response extends ConstructorExpression<HeartResponseDto.Response> {

    private static final long serialVersionUID = -1012777556L;

    public QHeartResponseDto_Response(com.querydsl.core.types.Expression<Long> heartNo, com.querydsl.core.types.Expression<Long> userNo, com.querydsl.core.types.Expression<Long> bookCode, com.querydsl.core.types.Expression<String> bookName, com.querydsl.core.types.Expression<String> regTm, com.querydsl.core.types.Expression<String> regDt, com.querydsl.core.types.Expression<String> bookAuthor, com.querydsl.core.types.Expression<String> bookPublisher) {
        super(HeartResponseDto.Response.class, new Class<?>[]{long.class, long.class, long.class, String.class, String.class, String.class, String.class, String.class}, heartNo, userNo, bookCode, bookName, regTm, regDt, bookAuthor, bookPublisher);
    }

}

