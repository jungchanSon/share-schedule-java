package com.schedule.share.user.application.service;

import com.schedule.share.user.login.exception.TokenValidFailedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@Primary
public class RefreshTokenProvider {
    private final Key key;

    public RefreshTokenProvider(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public RefreshToken createRefreshToken(String ci, Date expiry) {
        return new RefreshToken(ci, expiry, key);
    }

    public RefreshToken convertRefreshToken(String token) {
        return new RefreshToken(token, key);
    }

    public Authentication getAuthentication(RefreshToken refreshToken) {
        if (refreshToken.validate()) {
            Claims claims = refreshToken.getTokenClaims();
            log.debug("claims subject := [{}]", claims.getSubject());
            return new AbstractAuthenticationToken(null) {
                @Override
                public Object getCredentials() {
                    return null;
                }

                @Override
                public Object getPrincipal() {
                    return claims.getSubject();
                }

                @Override
                public boolean isAuthenticated() {
                    return true;
                }
            };
        } else {
            throw new TokenValidFailedException();
        }
    }
}