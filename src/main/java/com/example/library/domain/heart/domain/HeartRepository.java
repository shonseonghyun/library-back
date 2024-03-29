package com.example.library.domain.heart.domain;

import com.example.library.domain.heart.domain.dto.HeartResponseDto;

import java.util.List;
import java.util.Optional;

public interface HeartRepository {
    List<HeartResponseDto.Response> findHeartsByUserNo(Long usrNo);
    Heart findByUserNoAndBookNo(Long userNo,Long bookNo);
    Heart save(Heart heart);
    void deleteById(Long heartNo);
    Optional<Heart> getByUserNoAndBookNo(Long userNo, Long bookNo) ;

 }
