package com.example.library.domain.review.service.Impl;

import com.example.library.domain.book.repository.BookRepository;
import com.example.library.domain.review.dto.ReviewDto;
import com.example.library.domain.review.dto.ReviewWriteDto;
import com.example.library.domain.review.entity.ReviewEntity;
import com.example.library.domain.review.repository.ReviewRepository;
import com.example.library.domain.review.service.ReviewService;
import com.example.library.domain.user.repository.UserRepository;
import com.example.library.exception.ErrorCode;
import com.example.library.exception.exceptions.BookNotFoundException;
import com.example.library.exception.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<ReviewDto> getAllReview() {
        List<ReviewEntity> review = reviewRepository.findAll();

        return review.stream()
                .map(m -> new ReviewDto(m.getBook().getBookCode(), m.getUser().getUserNo(), m.getRegDate(), m.getReviewContent()))
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDto write(ReviewWriteDto reviewWriteDto, Long bookCode, Long userNo) {
        ReviewEntity review = ReviewEntity.builder()
                .book(bookRepository.findByBookCode(bookCode)
                        .orElseThrow(() -> new BookNotFoundException(ErrorCode.BOOKCODE_NOT_FOUND)))
                .user(userRepository.findByUserNo(userNo)
                        .orElseThrow(() -> new UserNotFoundException(ErrorCode.USERNO_NOT_FOUND)))
                .reviewContent(reviewWriteDto.getReviewContent())
                .regDate(reviewWriteDto.getRegDate())
                .build();
        reviewRepository.save(review);

        return ReviewDto.info(review);
    }
}