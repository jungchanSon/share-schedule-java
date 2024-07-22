package com.schedule.share.user.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class User {
    private long id;
    private long recentCalendarId;
    private String nickname;
    private String method;
    private String ci;
    private byte[] image;
    private LocalDateTime registeredAt;
    private LocalDateTime modifiedAt;

    public void updateRecentCalendarId(long recentCalendarId) {
        this.recentCalendarId = recentCalendarId;
    }
}
