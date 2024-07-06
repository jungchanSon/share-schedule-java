package com.schedule.share.user.adaptor.inbound.api.dto;

import java.time.LocalDateTime;

public class UserResponseDTO {

    public record Response(
        Long id,
        String nickname,
        String method,
        String ci,
        String image,
        LocalDateTime registeredAt,
        LocalDateTime lastModifiedAt
    ) {
    }
}
