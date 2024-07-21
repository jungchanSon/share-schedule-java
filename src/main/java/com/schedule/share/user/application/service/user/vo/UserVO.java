package com.schedule.share.user.application.service.user.vo;

import lombok.Builder;

import java.time.LocalDateTime;

public class UserVO {

    // query 용
    @Builder
    public record User(
        long id,
        long recentCalendarId,
        String nickname,
        String method,
        String ci,
        byte[] image,
        LocalDateTime registeredAt,
        LocalDateTime modifiedAt
    ) {}

    // command 용
    @Builder
    public record Save (
        long recentCalendarId,
        String nickname,
        String method,
        String ci,
        byte[] image
    ) {}
}
