package com.schedule.share.user.application.port.outbound;

import com.schedule.share.user.domain.RefreshToken;

public interface TokenQueryPort {

    RefreshToken getRefreshTokenByUserId(long userId);
}
