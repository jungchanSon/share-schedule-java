package com.schedule.share.user.adapter.inbound.api.dto.oauth2.code;

import com.schedule.share.common.enums.ProviderType;
import com.schedule.share.user.adapter.inbound.api.dto.OAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(ProviderType method, Map<String, Object> attributes) {
        // ProviderType 에 따라 적절한 OAuth2UserInfo 객체를 반환
        return switch (method) {
            case GOOGLE -> new GoogleOAuth2UserInfo(attributes);
            case NAVER -> new NaverOAuth2UserInfo(attributes);
            case KAKAO -> new KakaoOAuth2UserInfo(attributes);
            default -> throw new IllegalArgumentException("위치 : OAuth2UserInfoFactory | 예외 발생!");
        };
    }
}
