package com.example.library.domain.review.application.Impl;

import com.example.library.domain.book.domain.BookEntity;
import com.example.library.domain.book.domain.repository.BookRepository;
import com.example.library.domain.rent.domain.RentRepository;
import com.example.library.domain.review.application.ReviewService;
import com.example.library.domain.review.application.dto.*;
import com.example.library.domain.review.domain.ReviewEntity;
import com.example.library.domain.review.domain.dto.ReviewResponseDto;
import com.example.library.domain.review.domain.repository.ReviewRepository;
import com.example.library.domain.user.domain.UserEntity;
import com.example.library.domain.user.infrastructure.repository.SpringDataJpaUserRepository;
import com.example.library.exception.ErrorCode;
import com.example.library.exception.exceptions.ReviewAlreadyExistsByBookNoException;
import com.example.library.exception.exceptions.ReviewNotFoundException;
import com.example.library.exception.exceptions.ReviewWriteUnavailableException;
import com.example.library.exception.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final SpringDataJpaUserRepository springDataJpaUserRepository;
    private final RentRepository rentRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public void writeReview(ReviewWriteReqDto reviewWriteReqDto, Long userNo, Long bookNo) {
        //도서 당 1회 리뷰 작성 체크
        reviewRepository.findByUserUserNoAndBookBookCode(userNo,bookNo)
                .ifPresent(a-> {
                    throw new ReviewAlreadyExistsByBookNoException(ErrorCode.REVIEW_ALREADY_WRITE);
                });

        UserEntity selectedUser = springDataJpaUserRepository.findByUserNo(userNo).orElseThrow(()->new UserNotFoundException(ErrorCode.USERNO_NOT_FOUND));
        BookEntity selectedBook =bookRepository.findByBookNo(bookNo);

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

    
    //작성한 리뷰 + ( 작성해야할 리뷰)
    @Override
    @Transactional(readOnly = true)
    public ReviewPagingResDto getReviewsOfUser(Long userNo, Pageable pageable, Long cachedCount) {
        Page<ReviewResponseDto.Response> pageResult = reviewRepository.findReviewsByUserNo(userNo,pageable,cachedCount);
        List<ReviewResponseDto.Response> responseList = pageResult.getContent();
        int totalCount = (int)pageResult.getTotalElements();
        return new ReviewPagingResDto(totalCount,responseList);
//        Page<ReviewEntity> list = reviewRepository.findFetchJoinReviewsByUserNo(userNo,pageable);
//        List<ReviewEntity> reviewEntities = list.getContent();
//        Long totalCnt = list.getTotalElements();
//        List<UserReviewsResDto> userReviewsResDtos = reviewEntities.stream()
//                .map(reviewEntity -> UserReviewsResDto.from(reviewEntity))
//                .collect(Collectors.toList());

//        return UserReviewsResDtoWithTotalCnt.builder()
//                .reviewList(userReviewsResDtos)
//                .totalCnt(totalCnt)
//                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookReviewResDto> getReviewsOfBook(Long bookNo) {
        List<ReviewEntity> list  = reviewRepository.findFetchJoinReviewsByBookNo(bookNo);
        List<BookReviewResDto> bookReviewResDtos = list.stream().map(BookReviewResDto::from)
                .collect(Collectors.toList());
        return bookReviewResDtos;
    }

    @Override
    @Transactional
    public void updateReview(Long reviewNo, UpdateReviewReqDto updateReviewReqDto) {
        ReviewEntity selectedReview= reviewRepository.findByReviewNo(reviewNo)
                .orElseThrow(()-> new ReviewNotFoundException(ErrorCode.REVIEW_NOT_FOUND));
        selectedReview.updateReview(updateReviewReqDto.getUpdateReviewContent());
        reviewRepository.save(selectedReview);
    }
}
