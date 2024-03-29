package com.example.library.domain.rent.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RentStatusResponseDto {

    @Getter
    @NoArgsConstructor
    public static class Response{
        Long bookNo;
        String bookName;
        String rentDt;
        String haveToReturnDt;
        boolean extensionFlg;

        @Builder
        @QueryProjection
        public Response(Long bookNo, String bookName, String rentDt, String haveToReturnDt, boolean extensionFlg) {
            this.bookNo = bookNo;
            this.bookName = bookName;
            this.rentDt = rentDt;
            this.haveToReturnDt = haveToReturnDt;
            this.extensionFlg = extensionFlg;
        }

    }
}
