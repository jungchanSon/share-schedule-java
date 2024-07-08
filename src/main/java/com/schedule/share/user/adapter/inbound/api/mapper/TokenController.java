package com.schedule.share.user.adapter.inbound.api.mapper;

import com.schedule.share.common.properties.AppProperties;
import com.schedule.share.common.response.ApiResponse;
import com.schedule.share.common.utils.CookieUtil;
import com.schedule.share.common.utils.HeaderUtil;
import com.schedule.share.infra.repository.UserRefreshTokenRepository;
import com.schedule.share.user.application.service.AccessToken;
import com.schedule.share.user.application.service.AccessTokenProvider;
import com.schedule.share.user.application.service.RefreshToken;
import com.schedule.share.user.application.service.RefreshTokenProvider;
import com.schedule.share.user.domain.UserRefreshTokenEntity;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class TokenController {

    private final AppProperties appProperties; // 애플리케이션 속성 주입
    private final AccessTokenProvider accessTokenProvider; // 액세스 토큰
    private final RefreshTokenProvider refreshTokenProvider; // 리프레시 토큰
    private final UserRefreshTokenRepository userRefreshTokenRepository; // 리프레시 토큰 저장
    private final static long THREE_DAYS_MSEC = 259200000; // 3일의 밀리초 값
    private final static String REFRESH_TOKEN = "refresh_token"; // 리프레시 토큰 쿠키 이름

    // JWT 토큰을 이용한 사용자 로그인
    // https://kauth.kakao.com/oauth/authorize
    //
    @Operation(summary = "[사용자 로그인]", description = "JWT 토큰을 이용하여 사용자를 인증하고 액세스 토큰을 반환합니다.")
    @PostMapping("/login")
    public ApiResponse<String> login(HttpServletRequest request, HttpServletResponse response, @RequestParam String token) {
        AccessToken accessToken = accessTokenProvider.convertAccessToken(token);

        // 액세스 토큰이 유효하지 않은지 검증
        if (!accessToken.validate()) {
            return ApiResponse.invalidAccessToken();
        }

        Date now = new Date();
        // 액세스 토큰 생성
        AccessToken newAccessToken = accessTokenProvider.createAccessToken(null, new Date(now.getTime() + appProperties.getAuth().getTokenExpiry()));
        // 리프레시 토큰 만료 시간 가져오기
        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
        // 리프레시 토큰 생성
        RefreshToken newRefreshToken = refreshTokenProvider.createRefreshToken(
            appProperties.getAuth().getTokenSecret(),
            new Date(now.getTime() + refreshTokenExpiry)
        );

        // 리프레시 토큰 DB 저장
        UserRefreshTokenEntity userRefreshToken = new UserRefreshTokenEntity(null, newRefreshToken.getToken());
        try {
            userRefreshTokenRepository.saveAndFlush(userRefreshToken); // 리프레시 토큰 저장
        } catch (Exception e) {
            log.error("리프레시 토큰 저장 실패", e);
            return ApiResponse.fail();
        }

        // 쿠키 만료 시간 계산
        int cookieMaxAge = (int) refreshTokenExpiry / 60;
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN); // 기존 리프레시 토큰 쿠키 삭제
        CookieUtil.addCookie(response, REFRESH_TOKEN, newRefreshToken.getToken(), cookieMaxAge); // 새로운 리프레시 토큰 쿠키 추가

        return ApiResponse.success("token", newAccessToken.getToken()); // 액세스 토큰 반환
    }

    @Operation(summary = "[사용자 로그아웃]", description = "로그아웃하기")
    @PostMapping("/logout")
    // 사용자 로그아웃
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = CookieUtil.getCookie(request, "refresh_token")
                .map(Cookie::getValue)
                .orElse(null);

        // Cookie 에 담겨있는 refresh token 삭제
        CookieUtil.deleteCookie(request, response, "refresh_token");

        // DB에 저장되어 있는 refresh token 삭제
        userRefreshTokenRepository.deleteByRefreshToken(refreshToken);
    }

    @Operation(summary = "[액세스 토큰 갱신]", description = "유효한 리프레시 토큰을 이용하여 액세스 토큰을 갱신합니다.")
    @GetMapping("/refresh")
    public ApiResponse<String> refreshToken(HttpServletRequest request, HttpServletResponse response) {

        // 요청 헤더에서 액세스 토큰 문자열 추출
        String accessTokenStr = HeaderUtil.getAccessToken(request);
        AccessToken accessToken = accessTokenProvider.convertAccessToken(accessTokenStr);
        if (!accessToken.validate()) {
            return ApiResponse.invalidAccessToken();
        }

        // 만료된 액세스 토큰 클레임 가져오기
        Claims claims = accessToken.getExpiredTokenClaims();
        if (claims == null) {
            return ApiResponse.notExpiredTokenYet();
        }

        // 쿠키에서 리프레시 토큰 문자열 추출
        String refreshTokenStr = CookieUtil.getCookie(request, REFRESH_TOKEN)
                .map(Cookie::getValue)
                .orElse((null));
        if (refreshTokenStr == null) {
            return ApiResponse.notFound();
        }

        // 문자열을 리프레시 토큰 객체로 변환
        RefreshToken refreshToken = refreshTokenProvider.convertRefreshToken(refreshTokenStr);
        if (!refreshToken.validate()) {
            return ApiResponse.invalidRefreshToken();
        }

        // DB 에서 리프레시 토큰 찾기
        UserRefreshTokenEntity userRefreshToken = userRefreshTokenRepository.findByRefreshToken(refreshTokenStr);
        if (userRefreshToken == null) {
            return ApiResponse.notFound();
        }

        Date now = new Date();

        // 새로운 액세스 토큰 생성
        AccessToken newAccessToken = accessTokenProvider.createAccessToken(null, new Date(now.getTime() + appProperties.getAuth().getTokenExpiry()));

        // 리프레시 토큰의 남은 유효 시간 계산
        long validTime = refreshToken.getTokenClaims().getExpiration().getTime() - now.getTime();
        if (validTime <= THREE_DAYS_MSEC) {
            long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

            // 새로운 리프레시 토큰 생성
            refreshToken = refreshTokenProvider.createRefreshToken(
                    appProperties.getAuth().getTokenSecret(),
                    new Date(now.getTime() + refreshTokenExpiry)
            );

            // DB에 리프레시 토큰 업데이트
            userRefreshToken.setRefreshToken(refreshToken.getToken());

            int cookieMaxAge = (int) refreshTokenExpiry / 60;
            CookieUtil.deleteCookie(request, response, REFRESH_TOKEN); // 기존 리프레시 토큰 쿠키 삭제
            CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge); // 새로운 리프레시 토큰 쿠키 추가
        }

        return ApiResponse.success("token", newAccessToken.getToken()); // 새로운 액세스 토큰 반환
    }
}
