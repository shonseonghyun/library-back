package com.example.library.domain.heart.application.dto;

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
