package com.schedule.share.user.application.service;

import com.schedule.share.user.login.exception.TokenValidFailedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Collections;
import java.util.Date;

@Slf4j
@Component
@Primary
public class AccessTokenProvider {
    private final Key key;

    public AccessTokenProvider(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public AccessToken createAccessToken(String ci, Date expiry) {
        return new AccessToken(ci, expiry, key);
    }

    public AccessToken convertAccessToken(String token) {
        return new AccessToken(token, key);
    }

    public Authentication getAuthentication(AccessToken accessToken) {
        if (accessToken.validate()) {
            Claims claims = accessToken.getTokenClaims();
            log.debug("claims subject := [{}]", claims.getSubject());
            return new AbstractAuthenticationToken(Collections.singletonList(new SimpleGrantedAuthority("USER"))) {
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