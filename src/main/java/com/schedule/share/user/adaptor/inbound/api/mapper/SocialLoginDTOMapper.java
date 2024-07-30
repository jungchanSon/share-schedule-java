package com.schedule.share.user.adaptor.inbound.api.mapper;

import com.schedule.share.user.adaptor.inbound.api.dto.SocialLoginRequestDTO;
import com.schedule.share.user.adaptor.inbound.api.dto.SocialLoginResponseDTO;
import com.schedule.share.user.application.service.user.vo.SocialLoginVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SocialLoginDTOMapper {

    SocialLoginVO.NaverOauthCredential toVO(SocialLoginRequestDTO.NaverOauthCredential naverOauthCredential);

    SocialLoginResponseDTO.Response toResponse(SocialLoginVO.Token token);
}
