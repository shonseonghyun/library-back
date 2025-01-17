package com.example.library.global.security.oauth2.userInfo;

import com.example.library.global.security.oauth2.userInfo.OAuthUserInfo.userInfos.GoogleOAuthUserInfo;
import com.example.library.global.security.oauth2.userInfo.OAuthUserInfo.userInfos.KakaoOAuthUserInfo;
import com.example.library.global.security.oauth2.userInfo.OAuthUserInfo.userInfos.NaverOAuthUserInfo;
import com.example.library.global.security.oauth2.userInfo.OAuthUserInfo.OAuthUserInfo;
import com.example.library.domain.user.domain.UserEntity;
import com.example.library.domain.user.enums.SocialLoginType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class CustomOAuthAttributes {
    private String userNameAttributeName;
    private OAuthUserInfo oAuthUserInfo;

    public static CustomOAuthAttributes of(SocialLoginType socialLoginType, String userNameAttributeName, Map<String,Object> attributes){
        if(socialLoginType == SocialLoginType.NAVER){
            return ofNaver(userNameAttributeName,attributes);
        }
        else if (socialLoginType == SocialLoginType.KAKAO) {
            return ofKakao(userNameAttributeName,attributes);
        }
        else if (socialLoginType == SocialLoginType.GOOGLE) {
            return ofGoogle(userNameAttributeName,attributes);
        }

        return null;
    }

    public static UserEntity toEntity(SocialLoginType socialLoginType,OAuthUserInfo oAuthUserInfo){
        return UserEntity.createOAuth2User()
                .userId(oAuthUserInfo.getEmail())
                .userEmail(oAuthUserInfo.getEmail())
                .userPwd("tempPwd")
                .userName(oAuthUserInfo.getName())
                .providerId(oAuthUserInfo.getProviderId())
                .provider(socialLoginType)
                .useFlg(0)
                .build();
    }

    private static CustomOAuthAttributes ofNaver(String userNameAttributeName,Map<String,Object> attributes){
        return new CustomOAuthAttributes(userNameAttributeName,new NaverOAuthUserInfo(userNameAttributeName,attributes));
    }

    private static CustomOAuthAttributes ofKakao(String userNameAttributeName,Map<String,Object> attributes){
        return new CustomOAuthAttributes(userNameAttributeName,new KakaoOAuthUserInfo(userNameAttributeName,attributes));
    }

    private static CustomOAuthAttributes ofGoogle(String userNameAttributeName,Map<String,Object> attributes){
        return new CustomOAuthAttributes(userNameAttributeName,new GoogleOAuthUserInfo(userNameAttributeName,attributes));
    }
}
