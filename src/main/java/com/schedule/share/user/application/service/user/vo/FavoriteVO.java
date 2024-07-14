package com.schedule.share.user.application.service.user.vo;

import lombok.Builder;

import java.time.LocalDateTime;

public class FavoriteVO {

    @Builder
    public record Favorite(
            long id,
            long userId,
            long scheduleId,
            long calendarId,
            boolean isAllday,
            LocalDateTime scheduleStartDatetime,
            LocalDateTime scheduleEndDatetime,
            LocalDateTime createdAt
    ) {}

    @Builder
    public record save(
            long userId,
            long scheduleId,
            long calendarId,
            boolean isAllday,
            LocalDateTime scheduleStartDatetime,
            LocalDateTime scheduleEndDatetime
    ) {}
}
