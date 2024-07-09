package com.schedule.share.user.adaptor.inbound.api.dto;

import java.time.LocalDateTime;

public class UserResponseDTO {

    public record Response(
        long id,
        String nickname,
        String method,
        String ci,
        String image,
        LocalDateTime registeredAt,
        LocalDateTime lastModifiedAt
    ) {
    }
}
