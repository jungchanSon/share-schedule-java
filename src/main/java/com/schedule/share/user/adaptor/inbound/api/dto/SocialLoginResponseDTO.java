package com.schedule.share.user.adaptor.inbound.api.dto;

import lombok.Builder;

public class SocialLoginResponseDTO {

    @Builder
    public record Response(
            String accessToken,
            String refreshToken
    ) {
    }
}
