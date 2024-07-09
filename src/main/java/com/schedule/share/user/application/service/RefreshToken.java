package com.schedule.share.user.application.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Date;

public class RefreshToken extends Token {

    public RefreshToken(String id, Date expiry, Key key) {
        super(id, expiry, key);
    }

    public RefreshToken(String token, Key key) {
        super(token, key);
    }

    @Override
    protected String createToken(String id, Date expiry) {
        return Jwts.builder()
            .setSubject(id)
            .signWith(key, SignatureAlgorithm.HS256)
            .setExpiration(expiry)
            .compact();
    }
}