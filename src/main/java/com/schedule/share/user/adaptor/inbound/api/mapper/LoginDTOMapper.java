package com.schedule.share.user.adaptor.inbound.api.mapper;

import com.schedule.share.user.adaptor.inbound.api.dto.LoginRequestDTO;
import com.schedule.share.user.application.service.user.vo.LoginVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginDTOMapper {

    LoginVO.NaverOauthCredential toVO(LoginRequestDTO.NaverOauthCredentials naverOauthCredentials);

}
