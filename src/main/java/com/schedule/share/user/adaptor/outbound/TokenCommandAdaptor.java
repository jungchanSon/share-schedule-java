package com.schedule.share.user.adaptor.outbound;

import com.schedule.share.common.exception.Common404Exception;
import com.schedule.share.infra.rdb.entity.RefreshTokenEntity;
import com.schedule.share.infra.rdb.repository.RefreshTokenRepository;
import com.schedule.share.user.application.port.outbound.TokenCommandPort;
import com.schedule.share.user.domain.RefreshToken;
import com.schedule.share.user.domain.mapper.TokenMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Transactional
@RequiredArgsConstructor
public class TokenCommandAdaptor implements TokenCommandPort {

    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenMapper tokenMapper;

    @Override
    public RefreshToken createRefreshToken(RefreshToken refreshToken) {
        refreshTokenRepository.save(tokenMapper.refreshTokenToEntity(refreshToken));

        return refreshToken;
    }

    @Override
    public RefreshToken updateRefreshToken(long userId, String refreshToken) {
        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findByUserId(userId).orElseThrow(Common404Exception::new);

        refreshTokenEntity.updateTokenValue(refreshToken);

        return tokenMapper.refreshTokenEntityToDomain(refreshTokenEntity);
    }
}
