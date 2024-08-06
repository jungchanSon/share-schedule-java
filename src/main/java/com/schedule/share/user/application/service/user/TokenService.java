package com.schedule.share.user.application.service.user;

import com.schedule.share.common.exception.Common401Exception;
import com.schedule.share.common.util.JwtUtil;
import com.schedule.share.user.application.port.inbound.TokenServiceUseCase;
import com.schedule.share.user.application.port.outbound.TokenCommandPort;
import com.schedule.share.user.application.port.outbound.TokenQueryPort;
import com.schedule.share.user.application.service.user.vo.TokenVO;
import com.schedule.share.user.domain.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService implements TokenServiceUseCase {

    private final TokenCommandPort tokenCommandPort;
    private final TokenQueryPort tokenQueryPort;

    private final JwtUtil jwtUtil;

    @Override
    public TokenVO.AccessAndRefreshToken reissueAccessToken(String refreshToken) {
        long userId = jwtUtil.isExpire(refreshToken);// 리프래시 만료면 에러 -> 에러처리

        RefreshToken refreshTokenByUserId = tokenQueryPort.getRefreshTokenByUserId(userId);

        if (refreshToken.equals(refreshTokenByUserId.getRefreshToken())) {
            //access + refresh ->
            String newAccessToken = jwtUtil.generateAccessToken(userId);
            String newRefreshToken = jwtUtil.generateRefreshToken(userId);

            tokenCommandPort.updateRefreshToken(userId, newRefreshToken);

            return TokenVO.AccessAndRefreshToken.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .build();
        }
        throw new Common401Exception();
    }
}
