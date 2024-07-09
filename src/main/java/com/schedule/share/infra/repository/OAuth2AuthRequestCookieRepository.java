package com.schedule.share.infra.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.schedule.share.common.utils.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OAuth2AuthRequestCookieRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    // json 형식으로 보기
    private final ObjectMapper objectMapper = new ObjectMapper();
    // OAuth2 인증 요청을 저장하는 쿠키 이름
    public final static String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    // 로그인 후 리다이렉트 URI를 저장하는 쿠키 이름
    public final static String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
    // 리프레시 토큰을 저장하는 쿠키 이름
    public final static String REFRESH_TOKEN = "refresh_token";
    // 쿠키의 만료 시간 (초 단위)
    private final static int cookieExpireSeconds = 180;

    // HTTP 요청에서 OAuth2 인증 요청을 로드
    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        return CookieUtil.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
                .map(cookie -> {
                    OAuth2AuthorizationRequest authRequest = CookieUtil.deserialize(cookie, OAuth2AuthorizationRequest.class);
                    log.info("위치 : OAuth2AuthRequestCookieRepository | OAuth2 인증 요청 로드 성공: {}", authRequest);
                    return authRequest;
                })
                .orElse(null);
    }

    // OAuth2 인증 요청을 HTTP 응답에 저장
    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        // 인증 요청이 없으면 관련 쿠키 삭제
        if (authorizationRequest == null) {
            CookieUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
            CookieUtil.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
            CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
            return;
        }
        // OAuth2 인증 요청을 쿠키에 저장
        CookieUtil.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME, CookieUtil.serialize(authorizationRequest), cookieExpireSeconds);
        String redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME);
        if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
            // 로그인 후 리다이렉트 URI를 쿠키에 저장
            CookieUtil.addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME, redirectUriAfterLogin, cookieExpireSeconds);
        }

        try {
            String authorizationRequestString = objectMapper.writeValueAsString(authorizationRequest);
            log.info("위치 : OAuth2AuthRequestCookieRepository | OAuth2 인증 요청을 쿠키에 저장 : {}", authorizationRequestString);
        } catch (JsonProcessingException e) {
            log.error("OAuth2 인증 요청을 문자열로 변환하는 중 오류 발생", e);
        }
    }

    // HTTP 요청에서 OAuth2 인증 요청을 제거
    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        OAuth2AuthorizationRequest authorizationRequest = this.loadAuthorizationRequest(request);
        if (authorizationRequest != null) {
            try {
                String authorizationRequestString = objectMapper.writeValueAsString(authorizationRequest);
                log.info("위치 : OAuth2AuthRequestCookieRepository | OAuth2 인증 요청 제거: {}", authorizationRequestString);
            } catch (JsonProcessingException e) {
                log.error("OAuth2 인증 요청을 문자열로 변환하는 중 오류 발생", e);
            }
        }
        return authorizationRequest;
    }

    // 관련 쿠키를 모두 삭제
    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        CookieUtil.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        log.info("위치 : OAuth2AuthRequestCookieRepository | 모든 관련 쿠키 삭제 완료");
    }
}
