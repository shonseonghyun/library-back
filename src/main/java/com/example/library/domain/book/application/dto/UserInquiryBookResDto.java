package com.example.library.domain.book.application.dto;

import com.example.library.domain.book.domain.BookEntity;
import com.example.library.domain.book.enums.BookState;
import lombok.Getter;

@Getter
public class UserInquiryBookResDto {
    private Long bookNo;

    private String bookName;

    private String bookAuthor;

    private String bookContent;

    private BookState bookState;

    private String bookPublisher;

    private String isbn;

    private String pubDt;

    private String bookLocation;

    private BookImageResDto bookImage;

    private UserInquiryBookResDto(BookEntity bookEntity) {
        this.bookNo = bookEntity.getBookCode();
        this.bookName = bookEntity.getBookName();
        this.bookAuthor = bookEntity.getBookAuthor();
        this.bookContent = bookEntity.getBookContent();
        this.bookState = bookEntity.getBookState();
        this.bookPublisher = bookEntity.getBookPublisher();
        this.isbn = bookEntity.getIsbn();
        this.pubDt = bookEntity.getPubDt();
        this.bookLocation = bookEntity.getBookLocation();
        this.bookImage= BookImageResDto.from(bookEntity.getBookImage());
    }

    public static UserInquiryBookResDto from (BookEntity bookEntity){
        return new UserInquiryBookResDto(bookEntity);
    }
}
