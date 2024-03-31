package com.example.library.domain.review.controller;

import com.example.library.domain.review.dto.ReviewDto;
import com.example.library.domain.review.dto.ReviewWriteReqDto;
import com.example.library.domain.review.service.ReviewService;
import com.example.library.exception.ErrorCode;
import com.example.library.global.response.ApiResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@Tag(name = "review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/getAll")
    public ApiResponseDto getAllReview() {
        List<ReviewDto> reviewDtos = reviewService.getAllReview();
        return ApiResponseDto.createRes(ErrorCode.SUC, reviewDtos);
    }

    @PostMapping("/user/{userNo}/book/{bookNo}")
    public ApiResponseDto writeReview(@Valid @RequestBody ReviewWriteReqDto reviewWriteReqDto, @PathVariable("userNo")Long userNo, @PathVariable("bookNo") Long bookNo) {
        reviewService.writeReview(reviewWriteReqDto, userNo, bookNo);
        return ApiResponseDto.createRes(ErrorCode.SUC);
    }

    @DeleteMapping("/{reviewNo}")
    public ApiResponseDto deleteReview(  @PathVariable("reviewNo") Long reviewNo ) {
        reviewService.deleteReview(reviewNo);
        return ApiResponseDto.createRes(ErrorCode.SUC);
    }
}
