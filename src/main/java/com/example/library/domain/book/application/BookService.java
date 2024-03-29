package com.example.library.domain.book.application;

import com.example.library.domain.book.application.dto.BookAddDto;
import com.example.library.domain.book.application.dto.BookDto;
import com.example.library.domain.book.domain.BookEntity;

public interface BookService {
    //    BookDto detailSearchByBookAuthor(String bookAuthor);
//    BookDto detailSearchByBookName(String bookName);
//    BookSimpleDto simpleSearchByBookAuthor(String bookAuthor);
//    BookSimpleDto simpleSearchByBookName(String bookName);
    BookDto update(BookDto bookDto, Long bookCode);

    //    void delete(Long bookCode);
    BookAddDto add(BookAddDto bookAddDto);

    //특정 도서 정보 조회
    BookEntity inquiryBook(Long bookCode);
}
