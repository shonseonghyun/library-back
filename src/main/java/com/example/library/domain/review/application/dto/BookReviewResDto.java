package com.example.library.domain.review.application.dto;

import com.example.library.domain.review.domain.ReviewEntity;
import lombok.Getter;

@Getter
public class BookReviewResDto {
    private Long reviewNo;

    private Long userNo;

    private String userId;

    private String reviewContent;

    private String regDt;

    private String regTm;

    private String modifiedDt;

    private String modifiedTm;

    public BookReviewResDto(ReviewEntity  reviewEntity) {
        this.reviewNo = reviewEntity.getReviewNo();
        this.userNo = isUserEntityNull(reviewEntity) ? null : reviewEntity.getUser().getUserNo();
        this.userId = isUserEntityNull(reviewEntity) ? getDeleteUserId() : reviewEntity.getUser().getUserId();
        this.reviewContent = reviewEntity.getReviewContent();
        this.regDt = reviewEntity.getCreatedDt();
        this.regTm = reviewEntity.getCreatedTm();
        this.modifiedDt = reviewEntity.getModifiedDt();
        this.modifiedTm = reviewEntity.getModifiedTm();
    }

    public static BookReviewResDto from(ReviewEntity reviewEntity){
        return new BookReviewResDto(reviewEntity);
    }

    private boolean isUserEntityNull(ReviewEntity reviewEntity){
        return reviewEntity.getUser() == null;
    }

    private String getDeleteUserId(){
        return "UNKNOWN";
    }

}
