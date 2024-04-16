package com.example.library.domain.book.enums;

import com.example.library.exception.ErrorCode;
import com.example.library.exception.exceptions.CategoryNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum InquiryCategory {
    BOOK_NAME("bookName"),
    BOOK_AUTHOR("bookAuthor")
    ;

    private final String category;

    public static InquiryCategory getCategory(String category){
        return Arrays.stream(InquiryCategory.values())
                .filter(inquiryCategory -> inquiryCategory.getCategory().equals(category))
                .findFirst()
                .orElseThrow(()->new CategoryNotFoundException(ErrorCode.CATEGORY_NOT_FOUND));
    }
}
