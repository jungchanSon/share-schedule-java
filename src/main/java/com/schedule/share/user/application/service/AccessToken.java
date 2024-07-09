package com.schedule.share.user.application.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import java.security.Key;
import java.util.Date;

@Slf4j
@Getter
public class AccessToken extends Token {

    public AccessToken(String id, Date expiry, Key key) {
        super(id, expiry, key);
    }

    public AccessToken(String token, Key key) {
        super(token, key);
    }

    @Override
    protected String createToken(String id, Date expiry) {
        String token = Jwts.builder()
            .setSubject(id)
            .signWith(key, SignatureAlgorithm.HS256)
            .setExpiration(expiry)
            .compact();
        log.info("위치 : AccessToken | 토큰발급 : {}", token);

        return token;
    }
}