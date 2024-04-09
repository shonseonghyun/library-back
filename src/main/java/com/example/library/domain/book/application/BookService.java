package com.example.library.domain.book.application;

import com.example.library.domain.book.application.dto.BookModifiyReqDto;
import com.example.library.domain.book.application.dto.UserInquiryBookResDto;
import com.example.library.domain.book.domain.BookEntity;
import org.springframework.data.domain.PageRequest;

public interface BookService {
    //    BookDto detailSearchByBookAuthor(String bookAuthor);
//    BookDto detailSearchByBookName(String bookName);
//    BookSimpleDto simpleSearchByBookAuthor(String bookAuthor);
//    BookSimpleDto simpleSearchByBookName(String bookName);
//    BookDto update(BookDto bookDto, Long bookCode);

    //    void delete(Long bookCode);
    BookModifiyReqDto add(BookModifiyReqDto bookModifiyReqDto);

    //특정 도서 정보조회(리뷰 포함)
    UserInquiryBookResDto inquiryBook(Long bookNo);
}
