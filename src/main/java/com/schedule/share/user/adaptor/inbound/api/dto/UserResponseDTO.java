package com.schedule.share.user.adaptor.inbound.api.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public class UserResponseDTO {

    @Builder
    public record Response(
        long id,
        String nickname,
        String method,
        String ci,
        String image,
        LocalDateTime registeredAt,
        LocalDateTime modifiedAt
    ) {
    }
}
