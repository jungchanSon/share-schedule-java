package com.schedule.share.user.application.port.outbound;

import com.schedule.share.user.domain.RefreshToken;

public interface TokenCommandPort {

    RefreshToken createRefreshToken(RefreshToken refreshToken);
    RefreshToken updateRefreshToken(long userId, String refreshToken);
}
