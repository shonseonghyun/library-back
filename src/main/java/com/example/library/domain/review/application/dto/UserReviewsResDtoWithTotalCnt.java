package com.example.library.domain.review.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class UserReviewsResDtoWithTotalCnt {
    Long totalCnt;
    List<UserReviewsResDto> reviewList;
}
