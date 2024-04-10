package com.example.library.global.security.login.filter;

import com.example.library.domain.user.enums.UserGrade;
import com.example.library.domain.user.service.UserService;
import com.example.library.global.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        UsernamePasswordAuthenticationToken authenticationToken= null;
        final String accessTokenWithPrefix = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("accessToken : {}", accessTokenWithPrefix);

        if(JwtUtil.validateAccessToken(accessTokenWithPrefix)) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = accessTokenWithPrefix.split(" ")[1];
        String userId = JwtUtil.getUserId(accessToken);
        log.info("userId : {}", userId);

        if(StringUtils.hasText(userId)){
            UserGrade userGrade = userService.getUserGrade(userId);
            authenticationToken = new UsernamePasswordAuthenticationToken(userId, null, List.of(new SimpleGrantedAuthority(String.valueOf(userGrade.getGrade()))));
        }

        // Detail 넣어주기
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }

//    private boolean validateAccessToken(String accessTokenWithPrefix){
//        if(accessTokenWithPrefix==null){
//            log.error("authorization is null");
//            return false;
//        } else if (accessTokenWithPrefix.startsWith("Bearer ")) {
//            log.error("authorization doesn't start with Bearer");
//            return false;
//        }
//        return true;
//    }
}
