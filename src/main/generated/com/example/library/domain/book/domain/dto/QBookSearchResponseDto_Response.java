package com.example.library.domain.book.domain.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.example.library.domain.book.domain.dto.QBookSearchResponseDto_Response is a Querydsl Projection type for Response
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QBookSearchResponseDto_Response extends ConstructorExpression<BookSearchResponseDto.Response> {

    private static final long serialVersionUID = -979204612L;

    public QBookSearchResponseDto_Response(com.querydsl.core.types.Expression<Long> bookNo, com.querydsl.core.types.Expression<String> bookName, com.querydsl.core.types.Expression<String> bookAuthor, com.querydsl.core.types.Expression<String> pubDt, com.querydsl.core.types.Expression<com.example.library.domain.book.enums.BookState> bookState, com.querydsl.core.types.Expression<String> originalFileName, com.querydsl.core.types.Expression<Long> fileSize, com.querydsl.core.types.Expression<String> filePath, com.querydsl.core.types.Expression<String> newFileName) {
        super(BookSearchResponseDto.Response.class, new Class<?>[]{long.class, String.class, String.class, String.class, com.example.library.domain.book.enums.BookState.class, String.class, long.class, String.class, String.class}, bookNo, bookName, bookAuthor, pubDt, bookState, originalFileName, fileSize, filePath, newFileName);
    }

}

