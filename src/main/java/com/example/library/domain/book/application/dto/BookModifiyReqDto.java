package com.example.library.domain.book.application.dto;

import com.example.library.domain.book.domain.BookEntity;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BookModifiyReqDto {

    @NotNull
    private String bookName;

    @NotNull
    private String bookAuthor;

    @NotNull
    private String bookContent;

    @NotNull
    private Integer bookState;

    @NotNull
    private String bookPublisher;

    @NotNull
    private String isbn;

    @NotNull
    private String pubDt;

    @NotNull
    private String bookLocation;

    private String bookImage;

    private BookModifiyReqDto(BookEntity book) {
        this.bookName = book.getBookName();
        this.bookAuthor = book.getBookAuthor();
        this.bookContent = book.getBookContent();
        this.bookState = book.getBookState().getStateNum();
        this.bookPublisher = book.getBookPublisher();
        this.isbn = book.getIsbn();
        this.pubDt = book.getPubDt();
        this.bookLocation = book.getBookLocation();
        this.bookImage = book.getBookImage();
    }

    public static BookModifiyReqDto add(BookEntity book) {
        return new BookModifiyReqDto(book);
    }
}
