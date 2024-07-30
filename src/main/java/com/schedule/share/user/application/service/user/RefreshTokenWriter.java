package com.schedule.share.user.application.service.user;

import com.schedule.share.user.application.port.inbound.RefreshTokenCommand;
import com.schedule.share.user.application.port.outbound.RefreshTokenCommandPort;
import com.schedule.share.user.domain.RefreshToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RefreshTokenWriter implements RefreshTokenCommand {

    private final RefreshTokenCommandPort refreshTokenCommandPort;

    @Override
    public RefreshToken update(long id, RefreshToken refreshToken) {

        refreshTokenCommandPort.update(id, refreshToken);

        return null;
    }
}
