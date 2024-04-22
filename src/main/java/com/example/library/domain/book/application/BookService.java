package com.example.library.domain.book.application;

import com.example.library.domain.book.application.dto.BookRegReqDto;
import com.example.library.domain.book.application.dto.UserInquiryBookResDto;
import com.example.library.domain.book.domain.dto.BookSearchPagingResDto;
import com.example.library.domain.book.enums.InquiryCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {
    BookSearchPagingResDto inquirySimpleCategory(InquiryCategory category, String inquiryWord, Pageable pageable,Long cachedCount);
    UserInquiryBookResDto inquiryBook(Long bookNo);
    Long regBook(BookRegReqDto bookRegReqDto, MultipartFile file);
    void removeBook(Long bookNo);
}
