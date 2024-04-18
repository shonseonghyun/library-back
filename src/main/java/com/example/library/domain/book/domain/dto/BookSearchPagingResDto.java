package com.example.library.domain.book.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BookSearchPagingResDto {
    private int totalCount;
//    private int currentPage;
    private List<BookSearchResponseDto.Response> bookList;
}
