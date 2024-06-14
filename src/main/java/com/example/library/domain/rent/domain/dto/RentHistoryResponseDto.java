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
        private Long historyNo;
        private Long bookNo;
        private String bookName;
        private String rentDt;
        private String haveToReturnDt;
        private String returnDt;
        private boolean extensionFlg;
        private RentState rentState;

        @Builder
        @QueryProjection
        public Response(Long historyNo,Long bookNo, String bookName, String rentDt, String haveToReturnDt, String returnDt, boolean extensionFlg, RentState rentState) {
            this.historyNo = historyNo;
            this.bookNo = bookNo;
            this.bookName = bookName;
            this.rentDt = rentDt;
            this.haveToReturnDt = haveToReturnDt;
            this.returnDt = returnDt;
            this.extensionFlg = extensionFlg;
            this.rentState = rentState;
        }

    }
}
