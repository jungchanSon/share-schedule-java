package com.schedule.share.user.application.port.inbound;

public interface LoginQuery {
    <T> String getCi(T naverOauthCredential);
}