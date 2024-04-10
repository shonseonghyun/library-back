package com.example.library.global.utils;

import com.example.library.exception.ErrorCode;
import com.example.library.exception.exceptions.ExpiredTokenException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.JsonParseException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    private static final String secretKey = "test";

    private static final Long expiredMsOfAccess =
            1000
//                    * 60 * 60
                    *
                    24L;
    private static final Long expiredMsOfRefresh = 1000 * 60 * 24 *30 * 60 * 24L;


    public static String getUserId(String token) {
        String userId;

        try{
            userId= Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .get("userId", String.class);
        }catch (JsonParseException e){
            log.error("accessToken parse Error");
            throw e;
        }
        return userId;
    }

    public static boolean isExpired(String accessTokenWithPrefix) {
        String accessToken = accessTokenWithPrefix.split(" ")[1];

        try{
            return  Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(accessToken)
                    .getBody()
                    .getExpiration()
                    .before(new Date()); //현재보다 만료가 이전인지 확인
        }catch (ExpiredJwtException e){
            log.error("accessToken is expired");
            throw new ExpiredTokenException(ErrorCode.ACCESSTOKEN_EXPIRED);
        }catch (MalformedJwtException e){
            log.error("accessToken parse Error");
            throw new JsonParseException();
        }
    }

    public static String createAccessToken(String userId) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .setExpiration(new Date(System.currentTimeMillis() + expiredMsOfAccess))
                .compact();
    }

    public static String createRefreshToken() {
        return Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .setExpiration(new Date(System.currentTimeMillis() + expiredMsOfRefresh))
                .compact();
    }

    public static boolean validateAccessToken(String accessTokenWithPrefix){
        if(accessTokenWithPrefix==null){
            log.error("accessToken is null");
            return true;
        } else if (!accessTokenWithPrefix.startsWith("Bearer ")) {
            log.error("accessToken doesn't start with Bearer");
            return true;
        } else if (isExpired(accessTokenWithPrefix)) {
            return true;
        }
        return false;
    }
}
