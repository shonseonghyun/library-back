package com.example.library.domain.user.service.dto;


import com.example.library.domain.review.entity.ReviewEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserReviewsResDto {
    private Long reviewNo;
    private Long bookNo;
    private String bookName;
    private String reviewContent;
    private String regDt;
    private String regTm;

    public static UserReviewsResDto from(ReviewEntity reviewEntity){
        return UserReviewsResDto.builder()
                .reviewNo(reviewEntity.getReviewNo())
                .bookNo((reviewEntity.getBook().getBookCode()))
                .bookName((reviewEntity.getBook().getBookName()))
                .reviewContent(reviewEntity.getReviewContent())
                .regDt(reviewEntity.getCreatedDt())
                .regTm(reviewEntity.getCreatedTm())
                .build();
    }
}
