package com.example.library.domain.book.domain.dto;

import com.example.library.domain.book.application.dto.BookImageResDto;
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
        BookImageResDto bookImage = new BookImageResDto();

        @Builder
        @QueryProjection
        public Response(Long bookNo, String bookName, String bookAuthor, String pubDt, BookState bookState,String originalFileName,Long fileSize,String filePath,String newFileName) {
            this.bookNo = bookNo;
            this.bookName = bookName;
            this.bookAuthor = bookAuthor;
            this.pubDt = pubDt;
            this.bookState = bookState;

            //이미지
            this.bookImage.setFilePath(filePath);
            this.bookImage.setNewFileName(newFileName);
            this.bookImage.setOriginalFileName(originalFileName);
            this.bookImage.setFileSize(fileSize);
        }
    }
}
