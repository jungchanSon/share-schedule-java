package com.schedule.share.user.login.handler;

import com.schedule.share.common.enums.ProviderType;
import com.schedule.share.common.properties.AppProperties;
import com.schedule.share.common.utils.CookieUtil;
import com.schedule.share.infra.repository.OAuth2AuthRequestCookieRepository;
import com.schedule.share.infra.repository.UserRefreshTokenRepository;
import com.schedule.share.user.application.service.AccessToken;
import com.schedule.share.user.application.service.AccessTokenProvider;
import com.schedule.share.user.application.service.RefreshToken;
import com.schedule.share.user.application.service.RefreshTokenProvider;
import com.schedule.share.user.domain.UserRefreshTokenEntity;
import com.schedule.share.user.adapter.inbound.api.dto.OAuth2UserInfo;
import com.schedule.share.user.adapter.inbound.api.dto.oauth2.code.OAuth2UserInfoFactory;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.Date;
import java.util.Optional;

import static com.schedule.share.infra.repository.OAuth2AuthRequestCookieRepository.REDIRECT_URI_PARAM_COOKIE_NAME;
import static com.schedule.share.infra.repository.OAuth2AuthRequestCookieRepository.REFRESH_TOKEN;

/*
* OAuth2 인증 성공 시 처리 핸들러 클래스
*/

@Slf4j
@RequiredArgsConstructor
public class OAuth2AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AccessTokenProvider accessTokenProvider;
    private final RefreshTokenProvider refreshTokenProvider;
    private final AppProperties appProperties;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final OAuth2AuthRequestCookieRepository authorizationRequestRepository;

    // 인증 성공 시
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException, java.io.IOException {
        // 리다이렉트 URL
        String targetUrl = determineTargetUrl(request, response, authentication);

        // 응답이 이미 커밋되었는지 확인
        if (response.isCommitted()) {
            log.debug("위치 : OAuth2AuthRequestCookieRepository | 응답이 이미 커밋되었습니다. " + targetUrl + "(으)로 리다이렉트할 수 없습니다.");
            return;
        }
        // 인증 속성 제거
        clearAuthenticationAttributes(request, response);
        // 리다이렉트 수행
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    // 인증 성공 후 리다이렉트할 URL 결정
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new IllegalArgumentException("위치 : OAuth2AuthRequestCookieRepository | 허가되지 않은 리다이렉트 URI 입니다. 인증에 실패하였습니다.");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        ProviderType providerType = ProviderType.valueOf(authToken.getAuthorizedClientRegistrationId().toUpperCase());

        OidcUser user = ((OidcUser) authentication.getPrincipal());
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());
        // 사용자 권한 사용 X
//        Collection<? extends GrantedAuthority> authorities = ((OidcUser) authentication.getPrincipal()).getAuthorities();

        Date now = new Date();
        AccessToken accessToken = accessTokenProvider.createAccessToken(
            userInfo.getCi(),
            new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        // refresh 토큰 설정
        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

        RefreshToken refreshToken = refreshTokenProvider.createRefreshToken(
            appProperties.getAuth().getTokenSecret(),
            new Date(now.getTime() + refreshTokenExpiry)
        );

        // DB 저장
        UserRefreshTokenEntity userRefreshToken = userRefreshTokenRepository.findByCi(userInfo.getCi());
        if (userRefreshToken != null) {
            userRefreshToken.setRefreshToken(refreshToken.getToken());
        } else {
            userRefreshToken = new UserRefreshTokenEntity(userInfo.getCi(), refreshToken.getToken());
            userRefreshTokenRepository.saveAndFlush(userRefreshToken);
        }

        int cookieMaxAge = (int) refreshTokenExpiry / 60;

        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

        return UriComponentsBuilder.fromUriString(targetUrl)
            .queryParam("token", accessToken.getToken())
            .build().toUriString();
    }

    // 인증 속성 제거
    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    // 리다이렉트 URI 이 허가된 URI 인지 확인
    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        return appProperties.getOauth2().getAuthorizedRedirectUris()
            .stream()
            .anyMatch(authorizedRedirectUri -> {
                // Only validate host and port. Let the clients use different paths if they want to
                URI authorizedURI = URI.create(authorizedRedirectUri);
                return authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                        && authorizedURI.getPort() == clientRedirectUri.getPort();
            });
    }
}