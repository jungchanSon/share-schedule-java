package com.schedule.share.user.application.port.outbound;

import com.schedule.share.user.domain.RefreshToken;

public interface RefreshTokenCommandPort {

    RefreshToken create(RefreshToken refreshToken);
    RefreshToken update(long userId, RefreshToken refreshToken);
}
