package com.example.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

//@Configuration
////@EnableWebMvc
//public class CorsConfig
//        implements WebMvcConfigurer
//{
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOriginPatterns("*")
//                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
//                .allowedHeaders("*")
//                .exposedHeaders("Authorization")
//                .allowCredentials(true)
//                .maxAge(3600);
//    }
//}

@Configuration
public class CorsConfig {

    //    CORS (Cross Origin Resource Sharing) 은 같은 도메인 (스키마://호스트:포트) 가 같아야 한다는 SOP(Same Origin Policy) 에 의해 실행
//    다른 도메인에서도 허락하도록 아래 설정 추가
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*"); // 허용할 URL
        config.addAllowedHeader("*"); // 허용할 Header
        config.addAllowedMethod("*"); // 허용할 Http Method
        // ⭐CORS 는 해결했지만 프론트에 응답 헤더에 추가한 Authorization 이 전달되지 않는 문제 해결
        config.setExposedHeaders(Arrays.asList("Authorization", "Authorization-refresh"));
        source.registerCorsConfiguration("/**", config); // 모든 Url에 대해 설정한 CorsConfiguration 등록
        return new CorsFilter(source);
    }
}
