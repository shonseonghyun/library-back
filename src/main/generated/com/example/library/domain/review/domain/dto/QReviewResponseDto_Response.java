package com.example.library.domain.review.domain.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.example.library.domain.review.domain.dto.QReviewResponseDto_Response is a Querydsl Projection type for Response
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QReviewResponseDto_Response extends ConstructorExpression<ReviewResponseDto.Response> {

    private static final long serialVersionUID = -532427118L;

    public QReviewResponseDto_Response(com.querydsl.core.types.Expression<Long> reviewNo, com.querydsl.core.types.Expression<Long> bookNo, com.querydsl.core.types.Expression<String> bookName, com.querydsl.core.types.Expression<String> regDt, com.querydsl.core.types.Expression<String> regTm, com.querydsl.core.types.Expression<String> reviewContent, com.querydsl.core.types.Expression<String> uploadFileName, com.querydsl.core.types.Expression<String> uploadFilepath) {
        super(ReviewResponseDto.Response.class, new Class<?>[]{long.class, long.class, String.class, String.class, String.class, String.class, String.class, String.class}, reviewNo, bookNo, bookName, regDt, regTm, reviewContent, uploadFileName, uploadFilepath);
    }

}

