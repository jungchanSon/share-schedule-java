package com.schedule.share.user.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RefreshToken {
    private long id;
    private long userId;
    private String ci;
    private String refreshToken;
    private LocalDateTime createdAt;
}
