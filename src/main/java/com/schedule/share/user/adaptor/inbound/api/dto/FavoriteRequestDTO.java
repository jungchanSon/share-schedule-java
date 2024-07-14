package com.schedule.share.user.adaptor.inbound.api.dto;

import java.time.LocalDateTime;

public class FavoriteRequestDTO {

    public record Favorite(
            long userId,
            long scheduleId,
            long calendarId,
            boolean isAllday,
            LocalDateTime scheduleStartDatetime,
            LocalDateTime scheduleEndDatetime
    ) {
    }
}
