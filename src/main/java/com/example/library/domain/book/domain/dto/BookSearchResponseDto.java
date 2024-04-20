package com.example.library.domain.book.domain.dto;

import com.example.library.domain.book.enums.BookState;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BookSearchResponseDto {

    @Getter
    @NoArgsConstructor
    public static class Response{
        Long bookNo;
        String bookName;
        String bookAuthor;
        String pubDt;
        BookState bookState;
//        String bookImage;

        @Builder
        @QueryProjection
        public Response(Long bookNo, String bookName, String bookAuthor, String pubDt, BookState bookState
//                        ,String bookImage
        ) {
            this.bookNo = bookNo;
            this.bookName = bookName;
            this.bookAuthor = bookAuthor;
            this.pubDt = pubDt;
            this.bookState = bookState;
//            this.bookImage = bookImage;
        }
    }
}
