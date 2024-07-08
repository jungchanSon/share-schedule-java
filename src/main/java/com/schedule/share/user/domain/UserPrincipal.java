package com.schedule.share.user.domain;

import com.schedule.share.common.enums.ProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserPrincipal implements OAuth2User, OidcUser {

    // Oidc : OAuth 2.0을 확장하여 사용자 인증을 처리하는 프로토콜
    // OAuth2User : OAuth 2.0 기반의 사용자 인증을 처리하기 위한 인터페이스
    // UserPrincipal 클래스는 Spring Security OAuth2User, OidcUser 인터페이스를 구현
    // 소셜 로그인하여 인증된 사용자 정보를 관리

    private final String ci; // 사용자 CI(Customer Identifier)
    private final ProviderType method; // 소셜 로그인 제공자 유형
    private Map<String, Object> attributes; // OAuth2 사용자 속성

    // OAuth2 사용자 속성 반환
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // 사용자 권한 사용하지 않으므로 빈 권한 목록 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    // 사용자 이름 반환 사용하지 않으므로 null 반환
    @Override
    public String getName() {
        log.info("위치 : UserPrincipal | getName 호출됨 - 반환값: {}", ci);
        return ci;
    }

    // OIDC 클레임 반환
    @Override
    public Map<String, Object> getClaims() {
        return null;
    }

    // OIDC 사용자 정보 반환
    @Override
    public OidcUserInfo getUserInfo() {
        return null;
    }

    // OIDC ID 토큰 반환
    @Override
    public OidcIdToken getIdToken() {
        return null;
    }

    // User 객체로부터 UserPrincipal 생성
    public static UserPrincipal create(UserEntity user) {
        log.info("위치 : UserPrincipal | create 성공1 - 반환값 : {}", user);
        return new UserPrincipal(
                user.getCi(),
                user.getMethod()
        );
    }

    // User 객체로부터 UserPrincipal 객체를 생성
    public static UserPrincipal create(UserEntity user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = create(user);
        userPrincipal.setAttributes(attributes);
        log.info("위치 : UserPrincipal | create 성공2 - 반환값 : {}", user);
        return userPrincipal;
    }
}
