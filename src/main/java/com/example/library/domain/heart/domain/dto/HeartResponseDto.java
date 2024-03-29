package com.example.library.domain.heart.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

public class HeartResponseDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response{
        Long heartNo;
        Long userNo;
        Long bookCode;
        String bookName;
        String regTm;
        String regDt;
        String bookAuthor;
        String bookPublisher;
        String bookImage;

        @Builder
        @QueryProjection
        public Response(Long heartNo, Long userNo, Long bookCode, String bookName, String regTm, String regDt, String bookAuthor, String bookPublisher, String bookImage) {
            this.heartNo = heartNo;
            this.userNo = userNo;
            this.bookCode = bookCode;
            this.bookName = bookName;
            this.regTm = regTm;
            this.regDt = regDt;
            this.bookAuthor = bookAuthor;
            this.bookPublisher = bookPublisher;
            this.bookImage = bookImage;
        }
    }
}
