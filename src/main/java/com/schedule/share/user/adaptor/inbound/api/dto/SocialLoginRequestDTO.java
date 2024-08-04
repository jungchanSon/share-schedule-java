package com.schedule.share.user.adaptor.inbound.api.dto;

import lombok.Builder;

public class SocialLoginRequestDTO {

    @Builder
    public record NaverOauthCredential(
        String code,
        String state
    ) {
    }

    @Builder
    public record UserInfo(
            String nickname,
            byte[] image
    ) {
    }
}
