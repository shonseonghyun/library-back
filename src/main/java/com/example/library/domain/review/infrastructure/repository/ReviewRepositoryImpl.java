package com.example.library.domain.review.infrastructure.repository;

import com.example.library.domain.book.domain.QBookEntity;
import com.example.library.domain.rent.enums.RentState;
import com.example.library.domain.review.domain.QReviewEntity;
import com.example.library.domain.review.domain.ReviewEntity;
import com.example.library.domain.review.domain.dto.QReviewResponseDto_Response;
import com.example.library.domain.review.domain.dto.ReviewResponseDto;
import com.example.library.domain.review.domain.repository.ReviewRepository;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import static com.example.library.domain.rent.infrastructure.entity.QRentHistoryEntity.rentHistoryEntity;
import static com.example.library.domain.book.domain.QBookEntity.bookEntity;
import static com.example.library.domain.review.domain.QReviewEntity.reviewEntity;


@Slf4j
@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

    private static final Logger log = LoggerFactory.getLogger(ReviewRepositoryImpl.class);
    private final JPAQueryFactory jpaQueryFactory;
    private final SpringDataJpaReviewRepository springDataJpaReviewRepository;


    @Override
    public Page<ReviewEntity> findFetchJoinReviewsByUserNo(Long userNo, Pageable pageable) {
        return springDataJpaReviewRepository.findFetchJoinReviewsByUserNo(userNo,pageable);
    }

//    select book_no from rent_history
//    where user_no = 3
//    and rent_state=1
//    group by book_no
//    ;
    @Override
    public Page<ReviewResponseDto.Response> findReviewsByUserNo(Long userNo, Pageable pageable, Long cachedCount) {
        JPQLQuery<ReviewResponseDto.Response> query = jpaQueryFactory.select(new QReviewResponseDto_Response(reviewEntity.reviewNo,bookEntity.bookCode,bookEntity.bookName, reviewEntity.createdDt, reviewEntity.createdTm, reviewEntity.reviewContent,bookEntity.bookImage.newFileName,bookEntity.bookImage.filePath))
                .from(bookEntity)
                .join(bookEntity.bookImage)
                .leftJoin(reviewEntity)
                .on(reviewEntity.book.bookCode.eq(bookEntity.bookCode))
                .on(reviewEntity.user.userNo.eq(userNo))
                .where(bookEntity.bookCode.in(
                        jpaQueryFactory.select(rentHistoryEntity.bookNo)
                                .from(rentHistoryEntity)
                                .where(rentHistoryEntity.userNo.eq(userNo),rentHistoryEntity.rentState.ne(RentState.ON_RENT))
                                .groupBy(rentHistoryEntity.bookNo)
                                .fetch()
                ))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                ;

        List<ReviewResponseDto.Response> content = query.fetch();

        long totalCount = cachedCount != null ? cachedCount : query.fetchCount();

        return new PageImpl<>(content,pageable,totalCount);
    }

    @Override
    public List<ReviewEntity> findFetchJoinReviewsByBookNo(Long bookNo) {
        return springDataJpaReviewRepository.findFetchJoinReviewsByBookNo(bookNo);
    }

    @Override
    public Optional<ReviewEntity> findByUserUserNoAndBookBookCode(Long userNo, Long bookNo) {
        return springDataJpaReviewRepository.findByUserUserNoAndBookBookCode(userNo,bookNo);
    }

    @Override
    public ReviewEntity save(ReviewEntity reviewEntity) {
        return springDataJpaReviewRepository.save(reviewEntity);
    }

    @Override
    public void deleteById(Long reviewNo) {
        springDataJpaReviewRepository.deleteById(reviewNo);
    }

    @Override
    public Optional<ReviewEntity> findByReviewNo(Long reviewNo) {
        return springDataJpaReviewRepository.findByReviewNo(reviewNo);
    }
}
