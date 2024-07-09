package com.schedule.share.user.application.service.user.vo;

import lombok.Builder;

import java.time.LocalDateTime;

public class UserVO {

    // query 용
    @Builder
    public record User(
        long id,
        String nickname,
        String method,
        String ci,
        String image,
        LocalDateTime registeredAt,
        LocalDateTime modifiedAt
    ) {}

    // command 용
    @Builder
    public record Save (
        String nickname,
        String method,
        String ci,
        String image
    ) {}
}
