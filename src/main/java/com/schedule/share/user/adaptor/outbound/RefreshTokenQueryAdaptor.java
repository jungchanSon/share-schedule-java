package com.schedule.share.user.adaptor.outbound;

import com.schedule.share.infra.rdb.entity.RefreshTokenEntity;
import com.schedule.share.infra.rdb.repository.RefreshTokenRepository;
import com.schedule.share.user.application.port.outbound.RefreshTokenQueryPort;
import com.schedule.share.user.domain.RefreshToken;
import com.schedule.share.user.domain.mapper.RefreshTokenMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RefreshTokenQueryAdaptor implements RefreshTokenQueryPort {

    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenMapper refreshTokenMapper;

    @Override
    public RefreshToken get(long id) {
        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findById(id).orElseThrow();

        return refreshTokenMapper.refreshTokenEntityToDomain(refreshTokenEntity);
    }
}
