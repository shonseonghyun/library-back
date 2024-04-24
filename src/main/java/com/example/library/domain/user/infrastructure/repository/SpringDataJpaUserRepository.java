package com.example.library.domain.user.infrastructure.repository;

import com.example.library.domain.user.domain.UserEntity;
import com.example.library.domain.user.enums.SocialLoginType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserId(String userId);
    Optional<UserEntity> findByUserNo(Long userNo);
    Optional<UserEntity> findByProviderAndProviderIdAndUserEmail(SocialLoginType socialLoginType, String providerId, String userEmail);
    void deleteByUserNo(Long userNo);
    Optional<UserEntity> findByRefreshToken(String refreshToken);
}
