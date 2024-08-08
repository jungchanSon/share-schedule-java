package com.schedule.share.user.application.port.inbound;

import com.schedule.share.user.application.service.user.vo.TokenVO;

public interface TokenServiceUseCase{
    TokenVO.AccessAndRefreshToken reissueAccessToken(String refreshToken);
}
