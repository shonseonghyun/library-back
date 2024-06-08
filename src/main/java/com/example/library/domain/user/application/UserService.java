package com.example.library.domain.user.application;

import com.example.library.domain.user.application.dto.*;
import com.example.library.domain.user.domain.UserEntity;
import com.example.library.domain.user.enums.UserGrade;

import java.util.List;

public interface UserService {
    void join(UserJoinReqDto userJoinReqDto);
    UserLoginResDto login(UserLoginReqDto userLoginReqDto);
    UserSearchResDto getUserByUserNo(Long userNo);
    UserGrade getUserGrade(String userId);
    UserSearchResDto update(Long userNo, UserUpdateReqDto userUpdateReqDto);
    void delete(Long userNo);
    List<UserRentStatusResDto> getCurrentRentStatus(Long userNo);
    boolean checkExistUserId(String userId);
    String reissueAccessTokenWithRefreshToken(String refreshToken);
    void doAutoLogin(UserEntity selectedUser,String refreshToken);
    List<UserRentHistoryResDto> getRentHistory(Long userNo);
}
