package com.schedule.share.common.config;

import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.util.Base64;

//JWT Secret Key 랜덤 생성
@Slf4j
public class JwtSecretGenerator {
    public static void main(String[] args) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] secretKey = new byte[32]; // 256 bits
        secureRandom.nextBytes(secretKey);
        String encodedKey = Base64.getEncoder().encodeToString(secretKey);
        log.info("위치 : JwtSecretGenerator | 내가 생성한 JWT Secret Key : " + encodedKey);
    }
}
