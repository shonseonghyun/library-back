package com.example.library.global.security.oauth2.handler;

import com.example.library.domain.user.domain.UserEntity;
import com.example.library.domain.user.application.UserService;
import com.example.library.global.security.oauth2.principal.CustomOAuth2User;
import com.example.library.global.utils.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomOAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;

    @Override
    public void onAuthenticationSuccess (HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 로그인 성공");

        //1. 유저 이메일 추출
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        UserEntity selectedUser = customOAuth2User.getUser();

        //2. "유저이메일"을 통한 accessToken생성 및 refresh토큰 생성
        String accessToken = JwtUtil.createAccessToken(selectedUser.getUserEmail());
        String refreshToken = JwtUtil.createRefreshToken();
        userService.doAutoLogin(selectedUser,refreshToken);

        //3. redirectUrl get
        String redirectUrl = getRedirectUrl(accessToken);

        //4.쿠키 생성 및 세팅
        setCookies(response,refreshToken,selectedUser);

        //리다이렉트
        this.getRedirectStrategy().sendRedirect(request,response,redirectUrl);
    }

    private String getRedirectUrl(String accessToken){
        return "http://44.213.131.145/api/user/login/oauth"
                + "?"
                + "accessToken=" + accessToken + "&"
                ;
    }

    private void setCookies(HttpServletResponse response,String refreshToken,UserEntity user){
        Cookie userNoCookie = new Cookie("userNo",String.valueOf(user.getUserNo()));
        Cookie userIdCookie = new Cookie("userId",String.valueOf(user.getUserId()));
        Cookie refreshTokenCookie = new Cookie("refreshToken",refreshToken);

        userNoCookie.setPath("/");
        userIdCookie.setPath("/");
        refreshTokenCookie.setPath("/");

        response.addCookie(userNoCookie);
        response.addCookie(userIdCookie);
        response.addCookie(refreshTokenCookie);
    }
}
