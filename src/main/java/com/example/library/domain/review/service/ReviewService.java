package com.example.library.domain.review.service;

import com.example.library.domain.review.dto.ReviewDto;
import com.example.library.domain.review.dto.ReviewWriteReqDto;

import java.util.List;

public interface ReviewService {
    List<ReviewDto> getAllReview();
    void writeReview(ReviewWriteReqDto reviewWriteDto, Long userNo, Long bookNo);
    void deleteReview(Long reviewNo);
}
