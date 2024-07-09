package com.schedule.share.user.login.exception;

public class TokenValidFailedException extends RuntimeException {

    public TokenValidFailedException() {
        super("위치 : TokenValidFailedException | 토큰 생성 실패");
    }

    private TokenValidFailedException(String message) {
        super(message);
    }
}
