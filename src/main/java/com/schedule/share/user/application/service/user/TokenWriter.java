package com.schedule.share.user.application.service.user;

import com.schedule.share.user.application.port.inbound.TokenCommand;
import com.schedule.share.user.application.port.outbound.TokenCommandPort;
import com.schedule.share.user.domain.RefreshToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TokenWriter implements TokenCommand {

    private final TokenCommandPort tokenCommandPort;

    @Override
    public RefreshToken updateRefreshToken(long id, String refreshToken) {

        tokenCommandPort.updateRefreshToken(id, refreshToken);

        return null;
    }
}
