package com.schedule.share.user.adapter.inbound.api.mapper;

import com.schedule.share.user.domain.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// 테스트 완료 후 삭제
@Controller
public class TestController {

    @GetMapping("/test/login")
    public String getLoginPage() {
        return "SocialLoginTest";
    }

    @GetMapping("/test/login-success")
    public String loginSuccess(UserEntity user, @RequestParam String nickname, @RequestParam String image, @RequestParam String ci, @RequestParam String method) {
        return "SocialLoginSuccess";
    }
}
