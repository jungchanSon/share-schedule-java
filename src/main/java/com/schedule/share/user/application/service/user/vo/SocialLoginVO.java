package com.schedule.share.user.application.service.user.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SocialLoginVO {

    @Builder
    public record NaverOauthCredential(
            String code,
            String state
    ) {
    }

    @Builder
    public record NaverAccessToken(
            String access_token,
            String refresh_token,
            String token_type,
            String expires_in
    ) {
    }

    @Builder
    public record NaverCredential(
            String resultcode,
            String message,
            NaverCredentialResponse response
    ) {
    }

    @Builder
    public record NaverCredentialResponse(
            String id,
            String profile_image,
            String name
    ) {
    }

    @Builder
    public record UserInfo (
            String nickname,
            byte[] image
    ) {
    }

    @Builder
    public record Token(
            String accessToken,
            String refreshToken,
            UserVO.User user
    ) {
    }
}
