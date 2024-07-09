package com.schedule.share.user.application.service;

import com.schedule.share.common.enums.ProviderType;
import com.schedule.share.user.domain.UserPrincipal;
import com.schedule.share.infra.repository.UserRepository;
import com.schedule.share.user.domain.UserEntity;
import com.schedule.share.user.login.exception.OAuthProviderMissMatchException;
import com.schedule.share.user.adapter.inbound.api.dto.OAuth2UserInfo;
import com.schedule.share.user.adapter.inbound.api.dto.oauth2.code.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/*
 * OAuth2 인증 과정에서 사용자 정보를 처리
 * OAuth2UserService : Spring Security OAuth2에서 사용자 정보를 로드하는 데 사용되는 인터페이스
 * DefaultOAuth2UserService : OAuth2 제공자로부터 사용자 정보를 가져오고, 이를 OAuth2User 객체로 변환
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    // OAuth2 사용자 정보를 로드
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        // OAuth2 서비스 ID 구분코드 (구글, 카카오, 네이버) OK
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info("위치 : CustomOAuth2UserService | OAuth2 서비스 ID : {}", registrationId);

        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException ex) {
            log.error("위치 : CustomOAuth2UserService | 사용자 요청 처리 중 AuthenticationException 발생", ex);
            throw ex;
        } catch (Exception ex) {
            log.error("위치 : CustomOAuth2UserService | 사용자 요청 처리 중 Exception 발생", ex);
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    // OAuth2 사용자 정보를 처리
    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());
        UserEntity savedUser = userRepository.findByCi(userInfo.getCi());

        // 이미 사용자 정보가 있는 경우
        if (savedUser != null) {
            if (providerType != savedUser.getMethod()) {
                throw new OAuthProviderMissMatchException(
                    providerType + " 계정으로 가입하신 것 같습니다. " + savedUser.getMethod() + " 계정으로 로그인해주세요."
                );
            }
            updateUser(savedUser, userInfo);
        } else {
            savedUser = createUser(userInfo, providerType);
        }

        return UserPrincipal.create(savedUser, user.getAttributes());
    }

    // 새로운 사용자를 생성
    private UserEntity createUser(OAuth2UserInfo userInfo, ProviderType method) {
        UserEntity user = new UserEntity(
                userInfo.getCi(),
                userInfo.getNickname(),
                userInfo.getImage(),
                method
        );
        return userRepository.saveAndFlush(user);
    }

    // 기존 사용자를 업데이트
    private void updateUser(UserEntity user, OAuth2UserInfo userInfo) {
        // 사용자 닉네임이 변경된 경우 업데이트
        if (userInfo.getNickname() != null && !user.getNickname().equals(userInfo.getNickname())) {
            user.setNickname(userInfo.getNickname());
        }
        // 사용자 프로필 이미지가 변경된 경우 업데이트
        if (userInfo.getImage() != null && !user.getImage().equals(userInfo.getImage())) {
            user.setImage(userInfo.getImage());
        }
        userRepository.saveAndFlush(user);
    }

//    public UserResponseDto convertToUserResponseDto(UserEntity userEntity) {
//        return new UserResponseDto(userEntity);
//    }
}
