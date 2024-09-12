package com.schedule.share.user.adaptor.inbound.api.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class FavoriteRequestDTO {
    @Builder
    public record Favorite(
            long scheduleId,
            long calendarId,
            boolean isAllday,
            LocalDateTime scheduleStartDatetime,
            LocalDateTime scheduleEndDatetime
    ) {
    }

    @Builder
    public record BulkDelete(
            List<Long> list
    ) {
    }
}
