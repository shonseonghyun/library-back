package com.example.library.domain.user.domain.repository;

import com.example.library.domain.user.domain.UserEntity;
import com.example.library.domain.user.enums.SocialLoginType;

import java.util.Optional;

public interface UserRepository {
    UserEntity findByUserId(String userId);
    UserEntity findByUserNo(Long userNo);
    Optional<UserEntity> findByProviderAndProviderIdAndUserEmail(SocialLoginType socialLoginType, String providerId, String userEmail);
    void deleteByUserNo(Long userNo);
    UserEntity findByRefreshToken(String refreshToken);
    UserEntity save(UserEntity userEntity);
}
