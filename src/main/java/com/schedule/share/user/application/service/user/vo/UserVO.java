package com.schedule.share.user.application.service.user.vo;


import lombok.Getter;

import java.time.LocalDateTime;

public class UserVO {

    // query 용
    public record User(
        Long id,
        String nickname,
        String method,
        String ci,
        String image,
        LocalDateTime registeredAt,
        LocalDateTime modifiedAt
    ) {}

    // command 용
    public record Save (
        String nickname,
        String method,
        String ci,
        String image
    ) {}
}
