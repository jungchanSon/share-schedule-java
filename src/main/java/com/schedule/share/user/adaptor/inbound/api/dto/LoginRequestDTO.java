package com.schedule.share.user.adaptor.inbound.api.dto;

public class LoginRequestDTO {
    
    public record NaverOauthCredentials(
        String code,
        String state
    ) {
    }
}
