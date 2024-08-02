package com.schedule.share.user.application.port.outbound;

public interface SocialLoginPort<T> {
    String getCi(T oauthCredentials);
}
