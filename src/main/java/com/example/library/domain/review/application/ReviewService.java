package com.example.library.domain.review.application;

import com.example.library.domain.review.application.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {
    void writeReview(ReviewWriteReqDto reviewWriteDto, Long userNo, Long bookNo);
    void deleteReview(Long reviewNo);
    ReviewPagingResDto getReviewsOfUser(Long userNo, Pageable pageable, Long cachedCount);
    List<BookReviewResDto> getReviewsOfBook(Long bookNo);
    void updateReview(Long reviewNo, UpdateReviewReqDto updateReviewReqDto);
}
