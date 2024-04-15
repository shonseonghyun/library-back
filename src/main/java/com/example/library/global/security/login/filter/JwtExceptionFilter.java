package com.example.library.global.security.login.filter;

import com.example.library.exception.ErrorCode;
import com.example.library.exception.exceptions.ExpiredTokenException;
import com.example.library.global.response.ApiResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper om;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request,response); //JwtAuthenticationFilter로 간다.
        }catch (ExpiredTokenException e){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            om.writeValue(response.getOutputStream(), ApiResponseDto.createRes(e.getErrorCode()));
        }catch (JsonParseException e){
            om.writeValue(response.getOutputStream(), ApiResponseDto.createRes(ErrorCode.ACCESSTOKEN_PARSE_ERROR));
        }
    }
}
