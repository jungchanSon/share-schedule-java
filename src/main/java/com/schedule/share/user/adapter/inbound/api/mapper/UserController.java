package com.schedule.share.user.adapter.inbound.api.mapper;

import com.schedule.share.user.adapter.inbound.api.dto.UserResponseDto;
import com.schedule.share.user.application.service.UserService;
import com.schedule.share.user.domain.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "User API")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // @AuthenticationPrincipal: 현재 인증된 사용자 정보를 주입
    @Operation(summary = "[사용자 정보 조회]", description = "현재 인증된 사용자의 정보를 조회합니다.")
    @GetMapping
    public UserResponseDto getUserInfo(@AuthenticationPrincipal UserEntity user) {
        if (user == null) {
            throw new UsernameNotFoundException("인증된 사용자를 찾을 수 없습니다.");
        }
        return userService.getUserInfo(user.getNickname());
    }

    @Operation(summary = "[CI로 사용자 조회해보기]", description = "CI로 사용자의 정보가 조회되는지 확인합니다.")
    @GetMapping("/{ci}")
    public UserResponseDto getUserByCi(@Parameter @PathVariable String ci) {
        return userService.getUserByCi(ci);
    }
}
