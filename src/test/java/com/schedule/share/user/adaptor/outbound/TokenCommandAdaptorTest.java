package com.schedule.share.user.adaptor.outbound;

import com.schedule.share.common.util.JwtUtil;
import com.schedule.share.infra.rdb.repository.FavoriteRepository;
import com.schedule.share.infra.rdb.repository.RefreshTokenRepository;
import com.schedule.share.user.domain.RefreshToken;
import com.schedule.share.user.domain.mapper.FavoriteMapper;
import com.schedule.share.user.domain.mapper.FavoriteMapperImpl;
import com.schedule.share.user.domain.mapper.TokenMapper;
import com.schedule.share.user.domain.mapper.TokenMapperImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TokenCommandAdaptorTest {

    @Autowired
    private TokenCommandAdaptor tokenCommandAdaptor;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Spy
    private TokenMapper tokenMapper = new TokenMapperImpl();

//    private long id;
//    private long userId;
//    private String ci;
//    private String refreshToken;
//    private LocalDateTime createdAt;

    @Test
    @Transactional
    void createRefreshToken() {
        long userId = 10;

        String refreshTokenString = jwtUtil.generateRefreshToken(userId);

        RefreshToken refreshToken = RefreshToken.builder()
                .userId(userId)
                .ci("testTokenCI")
                .refreshToken(refreshTokenString)
                .build();

        RefreshToken savedRefreshToken = tokenCommandAdaptor.createRefreshToken(refreshToken);

        assertEquals(userId, savedRefreshToken.getUserId());
        assertEquals("testTokenCI", savedRefreshToken.getCi());
        assertEquals(refreshTokenString, savedRefreshToken.getRefreshToken());
    }

    @Test
    @Transactional
    void updateRefreshToken() {
        long userId = 10;

        String refreshTokenString = jwtUtil.generateRefreshToken(userId);

        RefreshToken refreshToken = RefreshToken.builder()
                .userId(userId)
                .ci("testTokenCI")
                .refreshToken(refreshTokenString)
                .build();

        RefreshToken savedRefreshToken = tokenCommandAdaptor.createRefreshToken(refreshToken);

        assertEquals(userId , savedRefreshToken.getUserId());
        assertEquals("testTokenCI", savedRefreshToken.getCi());
        assertEquals(refreshTokenString, savedRefreshToken.getRefreshToken());
    }
}