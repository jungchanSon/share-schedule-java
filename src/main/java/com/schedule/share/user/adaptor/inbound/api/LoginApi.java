package com.schedule.share.user.adaptor.inbound.api;

import com.schedule.share.user.adaptor.inbound.api.dto.LoginRequestDTO;
import com.schedule.share.user.adaptor.inbound.api.mapper.LoginDTOMapper;
import com.schedule.share.user.application.port.inbound.LoginCommand;
import com.schedule.share.user.application.port.inbound.LoginQuery;
import com.schedule.share.user.application.service.user.LoginService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "로그인", description = "로그인 API")
@RestController
@RequestMapping("/login/oauth2/code")
@RequiredArgsConstructor
public class LoginApi {

    private final LoginQuery loginQuery;
    private final LoginCommand loginCommand;
    private final LoginService loginService;

    private final LoginDTOMapper loginDTOMapper;

    @GetMapping(value = "/naver")
    public String getNaverCi(LoginRequestDTO.NaverOauthCredentials naverOauthCredentials) {
        return loginQuery.getNaverCi(loginDTOMapper.toVO(naverOauthCredentials));
    }
}
