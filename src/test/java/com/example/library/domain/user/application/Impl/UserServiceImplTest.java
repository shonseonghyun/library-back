package com.example.library.domain.user.application.Impl;

import com.example.library.domain.user.domain.UserEntity;
import com.example.library.domain.user.enums.SocialLoginType;
import com.example.library.domain.user.enums.UserGrade;
import com.example.library.domain.user.infrastructure.repository.SpringDataJpaUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    SpringDataJpaUserRepository springDataJpaUserRepository;

    @Test
    @DisplayName("UserEntity 내 provider필드, usergrade필드 attributeConverter 적용 테스트")
    public void AttributeConverter_디비TO엔티티_AND_엔티티TO디비_테스트(){
        Date date = new Date();
        String g = String.valueOf(date.getTime());
        //given
        UserEntity user = UserEntity.createOAuth2User()
                .userId(g)
                .userPwd("tempPwd")
                .userName("손성현")
                .useFlg(0)
                .provider(SocialLoginType.GOOGLE)
                .userEmail("thstjd11@gmail.com")
                .providerId("111111111111100001")
                .build()
         ;

        //when
        UserEntity saved = springDataJpaUserRepository.save(user);

        //then
        assertAll(
                () -> Assertions.assertThat(saved.getProvider()).isEqualTo(SocialLoginType.GOOGLE),
                () -> Assertions.assertThat(saved.getUserGrade()).isEqualTo(UserGrade.OFFICIALMEMBER)
        );

        springDataJpaUserRepository.delete(saved);

    }
}