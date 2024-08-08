package com.schedule.share.user.application.service.user.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenVO {

    @Builder
    public record AccessAndRefreshToken (
            String accessToken,
            String refreshToken
    ) {
    }
}
