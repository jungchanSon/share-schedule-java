package com.schedule.share.user.login.handler;

import com.schedule.share.common.utils.CookieUtil;
import com.schedule.share.infra.repository.OAuth2AuthRequestCookieRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.IOException;

import static com.schedule.share.infra.repository.OAuth2AuthRequestCookieRepository.REDIRECT_URI_PARAM_COOKIE_NAME;
/*
* OAuth2 인증 실패 시 처리 핸들러 클래스
*/
@Slf4j
@RequiredArgsConstructor
public class OAuth2AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final OAuth2AuthRequestCookieRepository authRequestRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 인증 실패 후 리다이렉트할 URL을 쿠키에서 가져오기
        String targetUrl = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse(("/"));
        log.info("위치 : OAuth2AuthFailureHandler | OAuth2 핸들러 인증실패 : {}", exception.getMessage());

        // 에러 메시지를 쿼리 파라미터로 추가하여 URL을 생성
        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", exception.getLocalizedMessage())
                .build().toUriString();

        // 인증 요청과 관련된 쿠키를 제거
        authRequestRepository.removeAuthorizationRequestCookies(request, response);

        // 사용자를 실패 URL 로 리다이렉트
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
