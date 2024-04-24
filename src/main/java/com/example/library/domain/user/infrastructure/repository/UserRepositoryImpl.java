package com.example.library.domain.user.infrastructure.repository;

import com.example.library.domain.user.domain.UserEntity;
import com.example.library.domain.user.domain.repository.UserRepository;
import com.example.library.domain.user.enums.SocialLoginType;
import com.example.library.exception.ErrorCode;
import com.example.library.exception.exceptions.RefreshTokenNotFountException;
import com.example.library.exception.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final SpringDataJpaUserRepository springDataJpaUserRepository;

    @Override
    public UserEntity findByUserId(String userId) {
        UserEntity selectedUserEntity = springDataJpaUserRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USERID_NOT_FOUND));
        return selectedUserEntity;
    }

    @Override
    public UserEntity findByUserNo(Long userNo) {
        UserEntity selectedUserEntity = springDataJpaUserRepository.findByUserNo(userNo)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USERNO_NOT_FOUND));
        return selectedUserEntity;
    }

    @Override
    public Optional<UserEntity> findByProviderAndProviderIdAndUserEmail(SocialLoginType socialLoginType, String providerId, String userEmail) {
        return springDataJpaUserRepository.findByProviderAndProviderIdAndUserEmail(socialLoginType,providerId,userEmail);
    }

    @Override
    public void deleteByUserNo(Long userNo) {
        springDataJpaUserRepository.deleteByUserNo(userNo);
    }

    @Override
    public UserEntity findByRefreshToken(String refreshToken) {
        UserEntity selectedUserEntity= springDataJpaUserRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()->new RefreshTokenNotFountException(ErrorCode.USERNO_NOT_FOUND));
        return selectedUserEntity;
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return springDataJpaUserRepository.save(userEntity);
    }
}
