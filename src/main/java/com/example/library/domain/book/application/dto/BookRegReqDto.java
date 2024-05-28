package com.example.library.domain.book.application.dto;

import com.example.library.domain.book.domain.BookEntity;
import com.example.library.domain.book.enums.BookState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRegReqDto {
    private String bookName;

    private String bookAuthor;

    private String bookContent;

    private String bookPublisher;

    private String isbn;

    private String pubDt;

    private String bookLocation;

    public BookEntity toBookEntity(){
        return BookEntity.builder()
                .bookName(this.bookName)
                .bookAuthor(this.bookAuthor)
                .bookContent(this.bookContent)
                .bookPublisher(this.bookPublisher)
                .bookState(BookState.RENT_AVAILABLE)
                .isbn(this.isbn)
                .pubDt(this.pubDt.replaceAll("-",""))
                .bookLocation(this.bookLocation)
                .build()
                ;
    }
}
