package com.schedule.share.user.application.service;

import com.schedule.share.infra.repository.UserRepository;
import com.schedule.share.user.adapter.inbound.api.dto.UserResponseDto;
import com.schedule.share.user.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 사용자 정보 조회
    public UserResponseDto getUserInfo(String ci) {
        // 사용자 CI로 사용자 정보 조회
        UserEntity targetUser = userRepository.findByCi(ci);
        return new UserResponseDto(targetUser);
    }

    // 사용자 CI로 사용자 정보 조회
    public UserResponseDto getUserByCi(String ci) {
        UserEntity user = userRepository.findByCi(ci);

        if (user == null) {
            return null;
        }
        return new UserResponseDto(user);
    }
}


