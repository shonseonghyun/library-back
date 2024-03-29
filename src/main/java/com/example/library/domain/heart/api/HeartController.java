package com.example.library.domain.heart.api;

import com.example.library.domain.heart.application.HeartService;
import com.example.library.domain.heart.application.dto.UserSelectHeartResDto;
import com.example.library.exception.ErrorCode;
import com.example.library.global.response.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HeartController {

    private final HeartService heartService;

    @GetMapping("/hearts/{userNo}")
    public ApiResponseDto getMyHeartList(@PathVariable Long userNo,@RequestParam(required = false) Long heartNo, @RequestParam int pageSize){
        UserSelectHeartResDto userSelectHeartResDto = heartService.getMyHeartList(heartNo,userNo,pageSize);
        return ApiResponseDto.createRes(ErrorCode.SUC, userSelectHeartResDto);
    }

    @PostMapping("/heart/{userNo}/book/{bookNo}")
    public ApiResponseDto heartBook(@PathVariable Long userNo,@PathVariable Long bookNo){
        heartService.registerHeartBook(userNo,bookNo);
        return ApiResponseDto.createRes(ErrorCode.SUC);
    }

    @DeleteMapping("/heart/{userNo}/book/{bookNo}")
    public ApiResponseDto removeHeartBook(@PathVariable Long userNo,@PathVariable Long bookNo) {
        heartService.removeHeartBook(userNo,bookNo);
        return ApiResponseDto.createRes(ErrorCode.SUC);
    }
}
