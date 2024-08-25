package com.schedule.share.user.adaptor.outbound;

import com.schedule.share.common.util.JwtUtil;
import com.schedule.share.infra.rdb.repository.RefreshTokenRepository;
import com.schedule.share.user.domain.RefreshToken;
import com.schedule.share.user.domain.mapper.TokenMapper;
import com.schedule.share.user.domain.mapper.TokenMapperImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TokenQueryAdaptorTest {

    @Autowired
    private TokenCommandAdaptor tokenCommandAdaptor;

    @Autowired
    private TokenQueryAdaptor tokenQueryAdaptor;

    @Autowired
    private JwtUtil jwtUtil;

    @Spy
    private TokenMapper tokenMapper = new TokenMapperImpl();

    @Test
    @Transactional
    void getRefreshTokenByUserId() {
        long userId = 100;
        String ci = "tokenCIForTest";

        String refreshTokenString = jwtUtil.generateRefreshToken(userId);

        RefreshToken refreshToken = RefreshToken.builder()
                .userId(userId)
                .ci(ci)
                .refreshToken(refreshTokenString)
                .build();

        tokenCommandAdaptor.createRefreshToken(refreshToken);

        RefreshToken refreshTokenByUserId = tokenQueryAdaptor.getRefreshTokenByUserId(userId);

        assertEquals(refreshTokenByUserId.getUserId(), userId);
        assertEquals(refreshTokenByUserId.getCi(), ci);
        assertEquals(refreshTokenByUserId.getRefreshToken(), refreshTokenString);
    }
}