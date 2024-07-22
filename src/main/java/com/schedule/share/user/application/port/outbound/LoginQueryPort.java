package com.schedule.share.user.application.port.outbound;

public interface LoginQueryPort<T> {
    String getCi(T oauthCredentials);
}
