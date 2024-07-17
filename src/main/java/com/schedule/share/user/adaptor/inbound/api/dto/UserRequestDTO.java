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
            byte[] image
    ) {
    }

    public record UserUpdate(
            String nickname,
            byte[] image
    ) {
    }

    public record RecentCalendar(
            long recentCalendarId
    ) {
    }
}
