package com.example.library.domain.heart.domain.dto;

import com.example.library.domain.book.application.dto.BookImageResDto;
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
        BookImageResDto bookImage = new BookImageResDto();


        @Builder
        @QueryProjection
        public Response(Long heartNo, Long userNo, Long bookCode, String bookName, String regTm, String regDt, String bookAuthor, String bookPublisher, String originalFileName,Long fileSize,String filePath,String newFileName) {
            this.heartNo = heartNo;
            this.userNo = userNo;
            this.bookCode = bookCode;
            this.bookName = bookName;
            this.regTm = regTm;
            this.regDt = regDt;
            this.bookAuthor = bookAuthor;
            this.bookPublisher = bookPublisher;

            //이미지
            this.bookImage.setFilePath(filePath);
            this.bookImage.setNewFileName(newFileName);
            this.bookImage.setOriginalFileName(originalFileName);
            this.bookImage.setFileSize(fileSize);
        }
    }
}
