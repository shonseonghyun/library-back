package com.example.library.domain.heart.application;

import com.example.library.domain.heart.application.dto.UserSelectHeartResDto;


public interface HeartService {
    UserSelectHeartResDto getMyHeartList(Long heartNo,Long userNo,int page);
    void registerHeartBook(Long userNo,Long bookNo);
    void removeHeartBook(Long userNo,Long bookNo);
}
