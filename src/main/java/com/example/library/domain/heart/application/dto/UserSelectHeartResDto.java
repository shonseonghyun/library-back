package com.example.library.domain.heart.application.dto;

import com.example.library.domain.heart.domain.dto.HeartResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class UserSelectHeartResDto {
    private Long userNo;
    List<HeartResponseDto.Response> heartList;
}
