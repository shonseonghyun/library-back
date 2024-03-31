package com.example.library.domain.review.service.Impl;

import com.example.library.domain.book.domain.BookEntity;
import com.example.library.domain.book.infrastructure.SpringDataJpaBookRepository;
import com.example.library.domain.review.dto.ReviewDto;
import com.example.library.domain.review.dto.ReviewWriteReqDto;
import com.example.library.domain.review.entity.ReviewEntity;
import com.example.library.domain.review.repository.ReviewRepository;
import com.example.library.domain.review.service.ReviewService;
import com.example.library.domain.user.entity.UserEntity;
import com.example.library.domain.user.repository.UserRepository;
import com.example.library.exception.ErrorCode;
import com.example.library.exception.exceptions.BookNotFoundException;
import com.example.library.exception.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final SpringDataJpaBookRepository bookRepository;

    @Override
    public List<ReviewDto> getAllReview() {
        List<ReviewEntity> review = reviewRepository.findAll();
        return review.stream()
                .map(m -> new ReviewDto(m.getBook().getBookCode(),
//                        m.getUser() == null ? "unknown" : m.getUser().getUserId(),
                        null,
                        m.getCreatedDt(), m.getCreatedDt(), m.getReviewContent()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void writeReview(ReviewWriteReqDto reviewWriteReqDto, Long userNo, Long bookNo) {
        UserEntity selectedUser = userRepository.findByUserNo(userNo).orElseThrow(()->new UserNotFoundException(ErrorCode.USERNO_NOT_FOUND));
        BookEntity selectedBook =bookRepository.findByBookCode(bookNo).orElseThrow(() -> new BookNotFoundException(ErrorCode.BOOKCODE_NOT_FOUND));

        ReviewEntity review = ReviewEntity.builder()
                .book(selectedBook)
                .reviewContent(reviewWriteReqDto.getReviewContent())
                .build();
        selectedUser.getReview().add(review); //merge

        userRepository.save(selectedUser);
    }

    @Override
    public void deleteReview(Long reviewNo) {
        reviewRepository.deleteById(reviewNo);
    }
}
