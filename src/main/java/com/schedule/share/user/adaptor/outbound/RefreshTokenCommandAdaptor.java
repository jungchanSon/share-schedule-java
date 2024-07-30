package com.schedule.share.user.adaptor.outbound;

import com.schedule.share.infra.rdb.entity.RefreshTokenEntity;
import com.schedule.share.infra.rdb.repository.RefreshTokenRepository;
import com.schedule.share.user.application.port.outbound.RefreshTokenCommandPort;
import com.schedule.share.user.domain.RefreshToken;
import com.schedule.share.user.domain.mapper.RefreshTokenMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Transactional
@RequiredArgsConstructor
public class RefreshTokenCommandAdaptor implements RefreshTokenCommandPort {

    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenMapper refreshTokenMapper;

    @Override
    public RefreshToken create(RefreshToken refreshToken) {
        refreshTokenRepository.save(refreshTokenMapper.refreshTokenToEntity(refreshToken));

        return refreshToken;
    }

    @Override
    public RefreshToken update(long  userId, RefreshToken refreshToken) {
        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findByUserId(userId);

        refreshTokenEntity.updateTokenValue(refreshToken);

        return refreshTokenMapper.refreshTokenEntityToDomain(refreshTokenEntity);
    }
}
