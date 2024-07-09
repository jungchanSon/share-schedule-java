package com.schedule.share.user.adaptor.inbound.api.dto;

public class UserRequestDTO {

    public record Search(
            String nickname
    ) {
    }

    public record User(
            String nickname,
            String method,
            String ci,
            String image
    ) {
    }
}
