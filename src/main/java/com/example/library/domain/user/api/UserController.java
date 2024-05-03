package com.example.library.domain.user.api;

import com.example.library.domain.user.application.dto.*;
import com.example.library.domain.user.application.UserService;
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

    @PostMapping("/auth/refreshToken/reissue")
    public ApiResponseDto reissueAccessToken(@RequestBody Map<String,String> map){
        log.info("reissue");
        String reissueAccessToken = userService.reissueAccessTokenWithRefreshToken(map.get("refreshToken"));
        return ApiResponseDto.createRes(ErrorCode.SUC, reissueAccessToken);
    }

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

    @PostMapping("/get/mypage")
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

    @GetMapping("/userId/{userId}/exist")
    public ApiResponseDto checkExistUserId(@PathVariable String userId){
        boolean existFlg = userService.checkExistUserId(userId);
        return ApiResponseDto.createRes(ErrorCode.SUC,existFlg);
    }
}
