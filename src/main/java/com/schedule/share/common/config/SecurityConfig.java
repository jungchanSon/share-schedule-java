package com.schedule.share.common.config;

import com.schedule.share.common.properties.AppProperties;
import com.schedule.share.common.properties.CorsProperties;
import com.schedule.share.infra.repository.OAuth2AuthRequestCookieRepository;
import com.schedule.share.infra.repository.UserRefreshTokenRepository;
import com.schedule.share.user.application.service.AccessTokenProvider;
import com.schedule.share.user.application.service.CustomOAuth2UserService;
import com.schedule.share.user.application.service.RefreshTokenProvider;
import com.schedule.share.user.login.exception.RestAuthEntryPoint;
import com.schedule.share.user.login.filter.TokenAuthFilter;
//import com.schedule.share.user.login.handler.AccessDeniedHandler;
import com.schedule.share.user.login.handler.OAuth2AuthFailureHandler;
import com.schedule.share.user.login.handler.OAuth2AuthSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsProperties corsProperties;
    private final AppProperties appProperties;
    private final AccessTokenProvider accessTokenProvider;
    private final RefreshTokenProvider refreshTokenProvider;
    private final CustomOAuth2UserService oAuth2UserService;
//    private final AccessDeniedHandler accessDeniedHandler;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션관리정책 STATELESS 설정
            .exceptionHandling(exceptionHandling -> exceptionHandling  // 예외처리 핸들러
                .authenticationEntryPoint(new RestAuthEntryPoint()) // 인증
//                .accessDeniedHandler(accessDeniedHandler) // 접근거부
            )
            .authorizeHttpRequests(authorize -> authorize // HTTP 요청권한
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll() // CORS Pre-flight 요청 허용
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll() // Swagger 경로에 대한 보안 제외
                .requestMatchers("/", "/test/login", "/test/success").permitAll() // 소셜 로그인 테스트
                .requestMatchers("/resources/**", "/static/**").permitAll() // 정적 리소스 경로 허용
                .requestMatchers("/api/**").permitAll() // /api/** 경로에 대한 보안 제외
                .anyRequest().authenticated() // 그 외 모든 요청 인증필요
            )
            .oauth2Login(oauth2 -> oauth2 // OAuth2 소셜 로그인
                .authorizationEndpoint(authorization -> authorization // 인증 엔드포인트
                    .baseUri("/oauth2/authorization")
                    .authorizationRequestRepository(oAuth2AuthRequestCookieRepository())
                )
                .redirectionEndpoint(redirection -> redirection // 리디렉션 엔드포인트
                    .baseUri("/*/oauth2/code/**")
                )
                .userInfoEndpoint(userInfo -> userInfo // 사용자정보 엔드포인트
                    .userService(oAuth2UserService)
                )
                .successHandler(oAuth2AuthSuccessHandler()) // 인증성공 핸들러
                .failureHandler(oAuth2AuthFailureHandler()) // 인증실패 핸들러
            );

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // AuthenticationManager Bean 정의
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    // 비밀번호 인코딩 사용안함
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    // JWT 인증 필터
    @Bean
    public TokenAuthFilter tokenAuthenticationFilter() {
        return new TokenAuthFilter(accessTokenProvider);
    }

    // OAuth2 인증 요청 저장소
    @Bean
    public OAuth2AuthRequestCookieRepository oAuth2AuthRequestCookieRepository() {
        return new OAuth2AuthRequestCookieRepository();
    }

    // OAuth2 인증 성공 핸들러
    @Bean
    public OAuth2AuthSuccessHandler oAuth2AuthSuccessHandler() {
        return new OAuth2AuthSuccessHandler(accessTokenProvider, refreshTokenProvider, appProperties, userRefreshTokenRepository,
                oAuth2AuthRequestCookieRepository()
        );
    }

    // OAuth2 인증 실패 핸들러
    @Bean
    public OAuth2AuthFailureHandler oAuth2AuthFailureHandler() {
        return new OAuth2AuthFailureHandler(oAuth2AuthRequestCookieRepository());
    }

    // CORS 설정
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource corsConfigSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedHeaders(Arrays.asList(corsProperties.getAllowedHeaders().split(",")));
        corsConfig.setAllowedMethods(Arrays.asList(corsProperties.getAllowedMethods().split(",")));
        corsConfig.setAllowedOrigins(Arrays.asList(corsProperties.getAllowedOrigins().split(",")));
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(corsConfig.getMaxAge());

        corsConfigSource.registerCorsConfiguration("/**", corsConfig);
        return corsConfigSource;
    }
}
