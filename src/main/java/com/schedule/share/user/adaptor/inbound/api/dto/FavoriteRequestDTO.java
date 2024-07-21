package com.schedule.share.user.adaptor.inbound.api.dto;

import java.time.LocalDateTime;
import java.util.List;

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

    public record BulkDelete(
            List<Long> list
    ) {
    }
}
