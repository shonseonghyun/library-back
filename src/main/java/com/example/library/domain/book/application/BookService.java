package com.example.library.domain.book.application;

import com.example.library.domain.book.application.dto.BookModifiyReqDto;
import com.example.library.domain.book.application.dto.UserInquiryBookResDto;
import com.example.library.domain.book.domain.dto.BookSearchResponseDto;
import com.example.library.domain.book.enums.InquiryCategory;

import java.util.List;

public interface BookService {
    List<BookSearchResponseDto.Response> inquirySimpleCategory(InquiryCategory category, String inquiryWord);
    BookModifiyReqDto add(BookModifiyReqDto bookModifiyReqDto);
    UserInquiryBookResDto inquiryBook(Long bookNo);
}
