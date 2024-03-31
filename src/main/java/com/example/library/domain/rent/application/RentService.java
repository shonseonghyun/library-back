package com.example.library.domain.rent.application;

import com.example.library.domain.rent.domain.dto.RentStatusResponseDto;

import java.util.List;

public interface RentService {
    public void rentBook(Long userNo,Long bookNo);
    public void returnBook(Long userNo,Long bookNo);
    public void extendBook(Long userNo,Long bookNo);
    public List<RentStatusResponseDto.Response> getCurrentRentStatus(Long userNo);
}
