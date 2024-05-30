package com.example.library.domain.review.application;

import com.example.library.domain.review.application.dto.*;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ReviewService {
    void writeReview(ReviewWriteReqDto reviewWriteDto, Long userNo, Long bookNo);
    void deleteReview(Long reviewNo);
    UserReviewsResDtoWithTotalCnt getReviewsOfUser(Long userNo, PageRequest pageRequest);
    List<BookReviewResDto> getReviewsOfBook(Long bookNo);
    void updateReview(Long reviewNo, UpdateReviewReqDto updateReviewReqDto);
}
