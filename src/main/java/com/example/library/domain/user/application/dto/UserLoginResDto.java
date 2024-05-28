package com.example.library.domain.user.application.dto;

import com.example.library.domain.user.enums.SocialLoginType;
import com.example.library.domain.user.enums.UserGrade;
import com.example.library.domain.user.domain.UserEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // Null 값인 필드 제외
public class UserLoginResDto {
    private Long userNo;

    private String userId;

    private String userName;

    private String tel;

    private String userEmail;

    private SocialLoginType provider;

    private String providerId;

    private String gender;

    private Integer useFlg;

    private UserGrade userGrade;

    private String accessToken;

    private String refreshToken;


    public UserLoginResDto(UserEntity user, String accessToken, String refreshToken) {
        this.userNo = user.getUserNo();
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.tel = user.getTel();
        this.userEmail = user.getUserEmail();
        this.provider = user.getProvider();
        this.gender = user.getGender();
        this.useFlg = user.getUseFlg();
        this.userGrade = user.getUserGrade();
        this.providerId = user.getProviderId();
        this.accessToken = accessToken;
        this.refreshToken= refreshToken;
    }

    public static UserLoginResDto from(UserEntity user, String accessToken,String refreshToken) {
        return new UserLoginResDto(user,accessToken,refreshToken);
    }
}
