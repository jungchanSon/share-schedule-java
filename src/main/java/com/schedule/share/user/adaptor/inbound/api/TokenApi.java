package com.schedule.share.user.adaptor.inbound.api;

import com.schedule.share.user.application.port.inbound.RefreshTokenCommand;
import com.schedule.share.user.application.port.inbound.RefreshTokenQuery;
import com.schedule.share.user.domain.RefreshToken;
import com.schedule.share.user.domain.mapper.RefreshTokenMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "로그인", description = "로그인 API")
@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenApi {

    private final RefreshTokenCommand refreshTokenCommand;
    private final RefreshTokenQuery refreshTokenQuery;

    private final RefreshTokenMapper refreshTokenMapper;

    @PostMapping("/refresh")
    public void accessRefresh(String refreshToken) {
    }
}
