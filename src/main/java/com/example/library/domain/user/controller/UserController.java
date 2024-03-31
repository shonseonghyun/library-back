package com.example.library.domain.user.controller;

import com.example.library.domain.user.dto.*;
import com.example.library.domain.user.service.UserService;
import com.example.library.domain.user.service.dto.UserRentStatusResDto;
import com.example.library.domain.user.service.dto.UserReviewsResDto;
import com.example.library.exception.ErrorCode;
import com.example.library.global.response.ApiResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "user")
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public ApiResponseDto join(@Valid @RequestBody UserJoinReqDto userJoinReqDto) {
        userService.join(userJoinReqDto);
        return ApiResponseDto.createRes(ErrorCode.SUC);
    }

    @PostMapping("/login")
    public ApiResponseDto login(@Valid @RequestBody UserLoginReqDto userLoginReqDto) {
        UserLoginResDto userLoginResDto = userService.login(userLoginReqDto);
        return ApiResponseDto.createRes(ErrorCode.SUC, userLoginResDto);
    }

    //내정보 수정
    @GetMapping("/get/mypage")
    public ApiResponseDto getUserByUserNo(@RequestBody Map<String,Long> map) {
        UserSearchResDto userSearchResDto = userService.getUserByUserNo(map.get("userNo"));
        return ApiResponseDto.createRes(ErrorCode.SUC, userSearchResDto);
    }

    @PutMapping("/update/{userNo}")
    public ApiResponseDto update(@PathVariable("userNo") Long userNo,@Valid @RequestBody UserUpdateDto userUpdateDto) {
        UserSearchResDto userSearchResDto = userService.update(userNo, userUpdateDto);
        return ApiResponseDto.createRes(ErrorCode.SUC, userSearchResDto);
    }

    @DeleteMapping("/delete/{userNo}")
    public ApiResponseDto delete(@PathVariable("userNo") Long userNo) {
        userService.delete(userNo);
        return ApiResponseDto.createRes(ErrorCode.SUC);
    }

    @GetMapping("/rentStatus/{userNo}")
    public ApiResponseDto getRentStatus(@PathVariable Long userNo){
        List<UserRentStatusResDto> userRentStatusResDtos = userService.getCurrentRentStatus(userNo);
        return ApiResponseDto.createRes(ErrorCode.SUC,userRentStatusResDtos);
    }

    //내가 작성한 리뷰
    @GetMapping("/get/{userNo}/review")
    public ApiResponseDto getReviewsOfUser(@PathVariable Long userNo) {
        List<UserReviewsResDto> userReviewsResDtos= userService.getReviewsOfUser(userNo);
        return ApiResponseDto.createRes(ErrorCode.SUC,userReviewsResDtos);
    }

    @GetMapping("/userId/{userId}/exist")
    public ApiResponseDto checkExistUserId(@PathVariable String userId){
        boolean existFlg = userService.checkExistUserId(userId);
        return ApiResponseDto.createRes(ErrorCode.SUC,existFlg);
    }
}
