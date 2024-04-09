package com.example.library.domain.review.service.Impl;

import com.example.library.domain.book.domain.BookEntity;
import com.example.library.domain.book.infrastructure.SpringDataJpaBookRepository;
import com.example.library.domain.rent.domain.RentManager;
import com.example.library.domain.rent.domain.RentRepository;
import com.example.library.domain.review.dto.ReviewWriteReqDto;
import com.example.library.domain.review.entity.ReviewEntity;
import com.example.library.domain.review.repository.ReviewRepository;
import com.example.library.domain.review.service.ReviewService;
import com.example.library.domain.review.service.dto.BookReviewResDto;
import com.example.library.domain.review.service.dto.UserReviewsResDto;
import com.example.library.domain.user.entity.UserEntity;
import com.example.library.domain.user.repository.UserRepository;
import com.example.library.exception.ErrorCode;
import com.example.library.exception.exceptions.BookNotFoundException;
import com.example.library.exception.exceptions.ReviewWriteUnavailableException;
import com.example.library.exception.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private final RentRepository rentRepository;
    private final SpringDataJpaBookRepository bookRepository;

    @Override
    @Transactional
    public void writeReview(ReviewWriteReqDto reviewWriteReqDto, Long userNo, Long bookNo) {
        UserEntity selectedUser = userRepository.findByUserNo(userNo).orElseThrow(()->new UserNotFoundException(ErrorCode.USERNO_NOT_FOUND));
        BookEntity selectedBook =bookRepository.findByBookCode(bookNo).orElseThrow(() -> new BookNotFoundException(ErrorCode.BOOKCODE_NOT_FOUND));
        Integer count = rentRepository.findRentHistoryCountWithReturn(userNo,bookNo);
        if(count>0){
            ReviewEntity review = ReviewEntity.builder()
                    .book(selectedBook)
                    .user(selectedUser)
                    .reviewContent(reviewWriteReqDto.getReviewContent())
                    .build();
            reviewRepository.save(review);
        }else{
            throw new ReviewWriteUnavailableException(ErrorCode.REVIEW_WRITE_AVAILABLE_DUE_TO_NEVER_RENT);
        }
    }

    @Override
    public void deleteReview(Long reviewNo) {
        reviewRepository.deleteById(reviewNo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserReviewsResDto> getReviewsOfUser(Long userNo,PageRequest pageRequest) {
        Page<ReviewEntity> list = reviewRepository.findFetchJoinReviewsByUserNo(userNo,pageRequest);
        List<ReviewEntity> reviewEntities = list.getContent();
        List<UserReviewsResDto> userReviewsResDtos = reviewEntities.stream()
                .map(reviewEntity -> UserReviewsResDto.from(reviewEntity))
                .collect(Collectors.toList());
        return userReviewsResDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookReviewResDto> getReviewsOfBook(Long bookNo, PageRequest pageRequest) {
        List<ReviewEntity> list  = reviewRepository.findFetchJoinReviewsByBookNo(bookNo);
        List<BookReviewResDto> bookReviewResDtos = list.stream().map(BookReviewResDto::from)
                .collect(Collectors.toList());
        return bookReviewResDtos;
    }
}
