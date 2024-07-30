package com.schedule.share.user.application.port.inbound;

import com.schedule.share.user.domain.RefreshToken;

public interface TokenCommand {

    RefreshToken updateRefreshToken(long id, RefreshToken refreshToken);
}
