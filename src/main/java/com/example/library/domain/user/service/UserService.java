package com.example.library.domain.user.service;

import com.example.library.domain.user.dto.*;
import com.example.library.domain.user.enums.UserGrade;
import com.example.library.domain.user.service.dto.UserRentStatusResDto;
import com.example.library.domain.user.service.dto.UserReviewsResDto;

import java.util.List;

public interface UserService {
    void join(UserJoinReqDto userJoinReqDto);
    UserLoginResDto login(UserLoginReqDto userLoginReqDto);
    UserSearchResDto getUserByUserNo(Long userNo);
    UserGrade getUserGrade(String userId);
    UserSearchResDto update(Long userNo, UserUpdateDto userUpdateDto);
    void delete(Long userNo);
    List<UserRentStatusResDto> getCurrentRentStatus(Long userNo);
    boolean checkExistUserId(String userId);
    List<UserReviewsResDto> getReviewsOfUser(Long userNo);

}
