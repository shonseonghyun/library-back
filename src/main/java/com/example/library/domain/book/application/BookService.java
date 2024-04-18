package com.example.library.domain.book.application;

import com.example.library.domain.book.application.dto.BookModifiyReqDto;
import com.example.library.domain.book.application.dto.UserInquiryBookResDto;
import com.example.library.domain.book.domain.dto.BookSearchPagingResDto;
import com.example.library.domain.book.enums.InquiryCategory;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookSearchPagingResDto inquirySimpleCategory(InquiryCategory category, String inquiryWord, Pageable pageable,Long cachedCount);
    BookModifiyReqDto add(BookModifiyReqDto bookModifiyReqDto);
    UserInquiryBookResDto inquiryBook(Long bookNo);
}
