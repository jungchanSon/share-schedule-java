package com.schedule.share.user.adapter.inbound.api.mapper;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("api/authorization")
public class OAuth2LoginController {

    @Operation(summary = "[구글로그인 테스트]", description = "추후기입")
    @GetMapping("/login/google")
    public RedirectView googleLogin(@RequestParam(required = false) String redirect_uri) {
        return new RedirectView("/oauth2/authorization/google?redirect_uri=" + redirect_uri);
    }

    @Operation(summary = "[네이버로그인 테스트]", description = "추후기입")
    @GetMapping("/login/naver")
    public RedirectView naverLogin(@RequestParam(required = false) String redirect_uri) {
        return new RedirectView("/oauth2/authorization/naver?redirect_uri=" + redirect_uri);
    }

    @Operation(summary = "[카카오로그인 테스트]", description = "추후기입")
    @GetMapping("/login/kakao")
    public RedirectView kakaoLogin(@RequestParam(required = false) String redirect_uri) {
        return new RedirectView("/oauth2/authorization/kakao?redirect_uri=" + redirect_uri);
    }

    @Operation(summary = "[구글회원가입 테스트]", description = "추후기입")
    @GetMapping("/signup/google")
    public RedirectView googleSignup(@RequestParam(required = false) String redirect_uri) {
        return new RedirectView("/oauth2/authorization/google?redirect_uri=" + redirect_uri);
    }

    @Operation(summary = "[네이버회원가입 테스트]", description = "추후기입")
    @GetMapping("/signup/naver")
    public RedirectView naverSignup(@RequestParam(required = false) String redirect_uri) {
        return new RedirectView("/oauth2/authorization/naver?redirect_uri=" + redirect_uri);
    }

    @Operation(summary = "[카카오회원가입 테스트]", description = "추후기입")
    @GetMapping("/signup/kakao")
    public RedirectView kakaoSignup(@RequestParam(required = false) String redirect_uri) {
        return new RedirectView("/oauth2/authorization/kakao?redirect_uri=" + redirect_uri);
    }
}
