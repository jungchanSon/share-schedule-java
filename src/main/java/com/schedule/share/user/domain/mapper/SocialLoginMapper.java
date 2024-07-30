package com.schedule.share.user.domain.mapper;

import com.schedule.share.user.application.service.user.vo.SocialLoginVO;
import com.schedule.share.user.domain.NaverLoginCredential;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SocialLoginMapper {

    NaverLoginCredential naverOauthCredentialVoToDomain (SocialLoginVO.NaverOauthCredential naverOauthCredential);
}
