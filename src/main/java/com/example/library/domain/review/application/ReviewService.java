package com.example.library.domain.review.application;

import com.example.library.domain.review.application.dto.ReviewWriteReqDto;
import com.example.library.domain.review.application.dto.BookReviewResDto;
import com.example.library.domain.review.application.dto.UserReviewsResDto;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ReviewService {
    void writeReview(ReviewWriteReqDto reviewWriteDto, Long userNo, Long bookNo);
    void deleteReview(Long reviewNo);
    List<UserReviewsResDto> getReviewsOfUser(Long userNo, PageRequest pageRequest);
    List<BookReviewResDto> getReviewsOfBook(Long bookNo);
}
