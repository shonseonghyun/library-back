package com.example.library.domain.review.domain.repository;

import com.example.library.domain.review.domain.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository {
    Page<ReviewEntity> findFetchJoinReviewsByUserNo(Long userNo, Pageable pageable);
    List<ReviewEntity> findFetchJoinReviewsByBookNo(Long bookNo);
    Optional<ReviewEntity> findByUserUserNoAndBookBookCode(Long userNo,Long bookNo);
    ReviewEntity save(ReviewEntity reviewEntity);
    void deleteById(Long reviewNo);
    Optional<ReviewEntity> findByReviewNo(Long reviewNo);
}
