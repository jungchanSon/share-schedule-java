package com.schedule.share.user.login.filter;

import com.schedule.share.user.application.service.AccessToken;
import com.schedule.share.user.application.service.AccessTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.schedule.share.common.utils.HeaderUtil;
import java.io.IOException;
/*
* 요청 당 한 번 실행되는 필터로, JWT 토큰을 통한 인증을 처리
*/
@Slf4j
@RequiredArgsConstructor
public class TokenAuthFilter extends OncePerRequestFilter {

    private final AccessTokenProvider tokenProvider;

    // 실제 필터링 작업을 수행
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 요청 헤더에서 액세스 토큰 가져오기
        String tokenStr = HeaderUtil.getAccessToken(request);

        // 액세스 토큰 문자열을 AuthToken 객체로 변환
        AccessToken token = tokenProvider.convertAccessToken(tokenStr);

        if (token.validate()) {
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 다음 필터를 호출
        filterChain.doFilter(request, response);
    }
}
