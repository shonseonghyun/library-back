package com.example.library.domain.user.infrastructure;

import com.example.library.domain.user.application.dto.UserRentHistoryResDto;
import com.example.library.domain.user.application.dto.UserRentStatusResDto;
import com.example.library.global.response.ApiResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="userOpenFeignClient", url="http://localhost:8000")
public interface UserOpenFeignClient {

    @GetMapping("/rent/currentRentStatus/user/{userNo}")
    public ApiResponseDto<List<UserRentStatusResDto>> getCurrentRentStatus(@PathVariable("userNo") Long userNo);

    @GetMapping("/rent/rentHistory/user/{userNo}")
    public ApiResponseDto<List<UserRentHistoryResDto>> getRentHistory(@PathVariable("userNo") Long userNo);

}
