package com.schedule.share.common.config;

import com.schedule.share.user.application.service.AccessTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    // JWT 서명에 사용할 비밀 키
    @Value("${jwt.secret}")
    private String secret;

    // AuthTokenProvider - JWT 토큰을 생성하고 검증
    @Bean
    public AccessTokenProvider jwtProvider() {
        return new AccessTokenProvider(secret);
    }
}
