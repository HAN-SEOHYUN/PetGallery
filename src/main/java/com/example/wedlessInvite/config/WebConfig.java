package com.example.wedlessInvite.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .addPathPatterns("/**") // 모든 요청 경로에 적용
                .excludePathPatterns(
                        "/login",
                        "/main",
                        "/register",
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/api/login",
                        "/api/register",
                        "/favicon.ico",
                        "/error"
                ); // 로그인, 정적 자원 등은 제외
    }
}
