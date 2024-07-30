package com.schedule.share.user.application.port.inbound;

import com.schedule.share.user.domain.RefreshToken;

public interface RefreshTokenCommand {

    RefreshToken update(long id, RefreshToken refreshToken);
}
