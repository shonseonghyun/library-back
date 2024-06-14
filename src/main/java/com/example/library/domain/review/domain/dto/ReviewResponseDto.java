package com.example.library.domain.review.domain.dto;

import com.example.library.domain.book.application.dto.BookImageResDto;
import com.example.library.domain.book.enums.BookState;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReviewResponseDto {

    @Getter
    @NoArgsConstructor
    public static class Response{
        Long reviewNo;
        Long bookNo;
        String bookName;
        String regDt;
        String regTm;
        String reviewContent;
        String uploadFileName;
        String uploadFilepath;

        @Builder
        @QueryProjection
        public Response(Long reviewNo, Long bookNo, String bookName, String regDt, String regTm, String reviewContent, String uploadFileName, String uploadFilepath) {
            this.reviewNo = reviewNo;
            this.bookNo = bookNo;
            this.bookName = bookName;
            this.regDt = regDt;
            this.regTm = regTm;
            this.reviewContent = reviewContent;
            this.uploadFileName = uploadFileName;
            this.uploadFilepath = uploadFilepath;
        }
    }
}
