package com.example.library.domain.rent.domain.dto;

import com.example.library.domain.rent.enums.RentState;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RentHistoryResponseDto {

    @Getter
    @NoArgsConstructor
    public static class Response{
        private Long bookNo;
        private String bookName;
        private String rentDt;
        private String haveReturnDt;
        private String returnDt;
        private boolean extensionFlg;
        private RentState rentState;

        @Builder
        @QueryProjection
        public Response(Long bookNo, String bookName, String rentDt, String haveReturnDt, String returnDt, boolean extensionFlg, RentState rentState) {
            this.bookNo = bookNo;
            this.bookName = bookName;
            this.rentDt = rentDt;
            this.haveReturnDt = haveReturnDt;
            this.returnDt = returnDt;
            this.extensionFlg = extensionFlg;
            this.rentState = rentState;
        }

    }
}
