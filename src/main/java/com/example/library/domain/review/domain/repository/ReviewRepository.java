package com.example.library.domain.review.domain.repository;

import com.example.library.domain.review.domain.ReviewEntity;
import com.example.library.domain.review.domain.dto.ReviewResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository {
    Page<ReviewEntity> findFetchJoinReviewsByUserNo(Long userNo, Pageable pageable);
    Page<ReviewResponseDto.Response> findReviewsByUserNo(Long userNo, Pageable pageable, Long cachedCount);
    List<ReviewEntity> findFetchJoinReviewsByBookNo(Long bookNo);
    Optional<ReviewEntity> findByUserUserNoAndBookBookCode(Long userNo,Long bookNo);
    ReviewEntity save(ReviewEntity reviewEntity);
    void deleteById(Long reviewNo);
    Optional<ReviewEntity> findByReviewNo(Long reviewNo);
}
