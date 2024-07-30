package com.schedule.share.user.adaptor.inbound.api;

import com.schedule.share.user.adaptor.inbound.api.dto.SocialLoginRequestDTO;
import com.schedule.share.user.adaptor.inbound.api.dto.SocialLoginResponseDTO;
import com.schedule.share.user.adaptor.inbound.api.dto.UserRequestDTO;
import com.schedule.share.user.adaptor.inbound.api.mapper.SocialLoginDTOMapper;
import com.schedule.share.user.adaptor.inbound.api.mapper.UserDTOMapper;
import com.schedule.share.user.application.port.inbound.LoginServiceUseCase;
import com.schedule.share.user.application.service.user.vo.SocialLoginVO;
import com.schedule.share.user.application.service.user.vo.UserVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "로그인", description = "로그인 API")
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class SocialLoginApi {

    private final LoginServiceUseCase loginServiceUseCase;
    private final SocialLoginDTOMapper socialLoginDTOMapper;
    private final UserDTOMapper userDTOMapper;

    @GetMapping(value = "/naver/sign")
    public SocialLoginResponseDTO.Response signup(SocialLoginRequestDTO.NaverOauthCredential naverOauthCredential, UserRequestDTO.User userInfo) {

        SocialLoginVO.NaverOauthCredential naverOauthVO = socialLoginDTOMapper.toVO(naverOauthCredential);

        UserVO.Save userVO = userDTOMapper.toVO(userInfo);
        SocialLoginVO.Token token = loginServiceUseCase.sign(naverOauthVO, userVO);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("accessToken", token.accessToken());
        httpHeaders.add("refreshToken", token.refreshToken());

        return socialLoginDTOMapper.toResponse(token);
    }

    @GetMapping(value = "/naver")
    public SocialLoginResponseDTO.Response naverLogin(SocialLoginRequestDTO.NaverOauthCredential naverOauthCredential) {

        SocialLoginVO.Token token = loginServiceUseCase.login(socialLoginDTOMapper.toVO(naverOauthCredential));

        return socialLoginDTOMapper.toResponse(token);
    }
}
