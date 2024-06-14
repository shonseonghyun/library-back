package com.example.library.domain.review.api;

import com.example.library.domain.review.application.dto.*;
import com.example.library.domain.review.application.ReviewService;
import com.example.library.exception.ErrorCode;
import com.example.library.global.response.ApiResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/review")
@Tag(name = "review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/user/{userNo}/book/{bookNo}")
    public ApiResponseDto writeReview(@Valid @RequestBody ReviewWriteReqDto reviewWriteReqDto, @PathVariable("userNo")Long userNo, @PathVariable("bookNo") Long bookNo) {
        reviewService.writeReview(reviewWriteReqDto, userNo, bookNo);
        return ApiResponseDto.createRes(ErrorCode.SUC);
    }

    @PutMapping("/{reviewNo}")
    public ApiResponseDto updateReview(@PathVariable("reviewNo") Long reviewNo
            , @RequestBody UpdateReviewReqDto updateReviewReqDto
//            ,@RequestBody Map<String,String> map
    ) {
        reviewService.updateReview(reviewNo,updateReviewReqDto);
        return ApiResponseDto.createRes(ErrorCode.SUC);
    }

    @DeleteMapping("/{reviewNo}")
    public ApiResponseDto deleteReview(@PathVariable("reviewNo") Long reviewNo) {
        reviewService.deleteReview(reviewNo);
        return ApiResponseDto.createRes(ErrorCode.SUC);
    }

    @GetMapping("/user/{userNo}")
    public ApiResponseDto getReviewsOfUser(@PathVariable Long userNo,@RequestParam(defaultValue = "1",name="page") Integer page,@RequestParam(defaultValue = "10",name="size") Integer size,@RequestParam(required = false,name = "cachedCount") Long cachedCount) {
        Pageable pageable = PageRequest.of(page-1, size);
        ReviewPagingResDto reviewPagingResDto = reviewService.getReviewsOfUser(userNo,pageable,cachedCount);
        return ApiResponseDto.createRes(ErrorCode.SUC, reviewPagingResDto);
    }

    @GetMapping("/book/{bookNo}")
    public ApiResponseDto getReviewsOfBook(@PathVariable Long bookNo) {
        List<BookReviewResDto> bookReviewResDtos = reviewService.getReviewsOfBook(bookNo);
        return ApiResponseDto.createRes(ErrorCode.SUC, bookReviewResDtos);
    }
}
