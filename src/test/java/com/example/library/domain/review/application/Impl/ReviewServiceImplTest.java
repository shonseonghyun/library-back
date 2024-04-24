package com.example.library.domain.review.application.Impl;

import com.example.library.domain.book.domain.BookEntity;
import com.example.library.domain.book.enums.BookState;
import com.example.library.domain.review.domain.ReviewEntity;
import com.example.library.domain.review.infrastructure.repository.SpringDataJpaReviewRepository;
import com.example.library.domain.user.domain.UserEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReviewServiceImplTest {
    
    @Autowired
    SpringDataJpaReviewRepository springDataJpaReviewRepository;
    
    @Test
    @DisplayName("리뷰 생성 시 리뷰날짜 자동생성 확인")
    void 리뷰작성시_작성일자_자동생성(){

        BookEntity book  =
                BookEntity.builder()
                        .bookCode(1L)
                        .bookName("테스트책1")
                        .isbn("1234")
                        .bookAuthor("저자1")
                        .bookContent("책 내용")
                        .bookPublisher("출판사1")
                        .bookLocation("A1")
                        .pubDt("20231111")
                        .bookState(BookState.RENT_AVAILABLE)
                        .build()
                ;
        UserEntity user = UserEntity.createOfficialUser()
                .userNo(11L)
                .userId("d")
                .userPwd("das")
                .userName("asd")
                .build()
                ;

        ReviewEntity reviewEntity = ReviewEntity
                .builder()
                .reviewContent("내용1")
                .book(book)
                .user(user)
                .build();
        springDataJpaReviewRepository.save(reviewEntity);
    }

}