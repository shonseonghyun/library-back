package com.example.library.domain.review.application.dto;

import com.example.library.domain.review.domain.dto.ReviewResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReviewPagingResDto {
    private int totalCount;
    private List<ReviewResponseDto.Response> reviewList;
}
