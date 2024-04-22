package com.example.library.domain.book.domain.repository;

import com.example.library.domain.book.domain.BookEntity;
import com.example.library.domain.book.domain.dto.BookSearchResponseDto;
import com.example.library.domain.book.enums.InquiryCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookRepository {
    BookEntity findByBookNo(Long bookNo);
    BookEntity save(BookEntity book);
    Page<BookSearchResponseDto.Response> findBooksBySimpleCategory(InquiryCategory category, String inquiryWord,Pageable pageable,Long cachedCount);
    void delete(BookEntity book);
}
