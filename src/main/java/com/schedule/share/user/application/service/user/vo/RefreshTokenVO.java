package com.schedule.share.user.application.service.user.vo;

import lombok.Builder;

public class RefreshTokenVO {

    @Builder
    public record RefreshToken () {

    }

    @Builder
    public record  save (
            long userId,
            String ci,
            String refreshToken
    ) {
    }
}
