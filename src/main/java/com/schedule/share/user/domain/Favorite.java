package com.schedule.share.user.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Favorite {
    private long id;
    private long userId;
    private long scheduleId;
    private long calendarId;
    private boolean isAllday;
    private LocalDateTime scheduleStartDatetime;
    private LocalDateTime scheduleEndDatetime;
    private LocalDateTime createdAt;
}