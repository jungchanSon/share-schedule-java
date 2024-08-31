package com.schedule.share.user.adaptor.inbound.api.dto;

import lombok.Builder;

public class TokenResponseDTO {

    @Builder
    public record AccessAndRefreshToken (
            String accessToken,
            String refreshToken
    ) {
    }
}
