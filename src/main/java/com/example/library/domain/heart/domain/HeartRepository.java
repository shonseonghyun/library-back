package com.example.library.domain.heart.domain;

import com.example.library.domain.heart.application.dto.HeartResponseDto;

import java.util.List;

public interface HeartRepository {
    List<HeartResponseDto.Response> findHeartsByUserNo(long usrNo);
    Heart findByUserNoAndBookNo(Long userNo,Long bookNo);
    Heart save(Heart heart);
    void deleteById(Long heartNo);
}
