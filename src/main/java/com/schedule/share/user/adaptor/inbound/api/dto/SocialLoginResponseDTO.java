package com.schedule.share.user.adaptor.inbound.api.dto;

import lombok.Builder;

public class SocialLoginResponseDTO {

    @Builder
    public record Response<T>(
            String accessToken,
            String refreshToken,
            T user
    ) {
    }
}
