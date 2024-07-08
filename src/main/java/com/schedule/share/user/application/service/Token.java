package com.schedule.share.user.application.service;

import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.security.Key;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public abstract class Token {
    // JWT 토큰을 생성, 검증, 파싱
    @Getter
    protected final String token; // JWT 토큰 문자열
    protected final Key key; // JWT 서명을 위한 키

    // 주어진 ID와 만료일, 키를 사용하여 AuthToken 객체를 생성
    protected Token(String id, Date expiry, Key key) {
        this.key = key;
        this.token = createToken(id, expiry);
    }

    // JWT 토큰을 생성
    protected abstract String createToken(String id, Date expiry);

    // JWT 토큰의 유효성을 검사
    public boolean validate() {
        return this.getTokenClaims() != null;
    }

    // JWT 토큰의 클레임을 반환
    public Claims getTokenClaims() {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key) // 서명 키 설정
                    .build()
                    .parseClaimsJws(token) // 토큰 파싱 및 검증
                    .getBody(); // 클레임 반환
        } catch (SecurityException e) {
            log.info("위치 : Token | 유효하지 않은 JWT 서명 | {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.info("위치 : Token | 유효하지 않은 JWT 토큰 | {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.info("위치 : Token | 만료된 JWT 토큰 | {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.info("위치 : Token | 지원되지 않는 JWT 토큰 | {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.info("위치 : Token | 잘못된 JWT 토큰 | {}", e.getMessage());
        }
        return null;
    }

    // 만료된 JWT 토큰의 클레임을 반환
    public Claims getExpiredTokenClaims() {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key) // 서명 키 설정
                    .build()
                    .parseClaimsJws(token) // 토큰 파싱 및 검증
                    .getBody(); // 클레임 반환
        } catch (ExpiredJwtException e) {
            log.info("위치 : Token | 만료된 JWT 토큰");
            return e.getClaims();
        }
        return null;
    }
}