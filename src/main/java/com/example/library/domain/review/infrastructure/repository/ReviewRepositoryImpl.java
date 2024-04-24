package com.example.library.domain.review.infrastructure.repository;

import com.example.library.domain.review.domain.ReviewEntity;
import com.example.library.domain.review.domain.repository.ReviewRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final SpringDataJpaReviewRepository springDataJpaReviewRepository;


    @Override
    public Page<ReviewEntity> findFetchJoinReviewsByUserNo(Long userNo, Pageable pageable) {
        return springDataJpaReviewRepository.findFetchJoinReviewsByUserNo(userNo,pageable);
    }

    @Override
    public List<ReviewEntity> findFetchJoinReviewsByBookNo(Long bookNo) {
        return springDataJpaReviewRepository.findFetchJoinReviewsByBookNo(bookNo);
    }

    @Override
    public ReviewEntity save(ReviewEntity reviewEntity) {
        return springDataJpaReviewRepository.save(reviewEntity);
    }

    @Override
    public void deleteById(Long reviewNo) {
        springDataJpaReviewRepository.deleteById(reviewNo);
    }
}
