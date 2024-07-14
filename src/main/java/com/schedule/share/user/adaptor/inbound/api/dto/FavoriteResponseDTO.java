package com.schedule.share.user.adaptor.inbound.api.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public class FavoriteResponseDTO {

    @Builder
    public record Response(
            long id,
            long userId,
            long scheduleId,
            long calendarId,
            boolean allday,
            LocalDateTime scheduleStartDatetime,
            LocalDateTime scheduleEndDatetime,
            LocalDateTime createdAt
    ) {
    }
}
