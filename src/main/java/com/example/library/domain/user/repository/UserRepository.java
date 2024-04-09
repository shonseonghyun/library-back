package com.example.library.domain.user.repository;

import com.example.library.domain.user.entity.UserEntity;
import com.example.library.domain.user.enums.SocialLoginType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserId(String userId);
    Optional<UserEntity> findByUserNo(Long userNo);
    Optional<UserEntity> findByProviderAndProviderIdAndUserEmail(SocialLoginType socialLoginType, String providerId, String userEmail);
    void deleteByUserNo(Long userNo);
}
