package com.schedule.share.user.application.port.outbound;

import com.schedule.share.user.domain.RefreshToken;

public interface RefreshTokenQueryPort {

    RefreshToken get (long id);
}
