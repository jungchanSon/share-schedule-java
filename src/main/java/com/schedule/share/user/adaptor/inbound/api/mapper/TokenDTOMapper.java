package com.schedule.share.user.adaptor.inbound.api.mapper;

import com.schedule.share.user.adaptor.inbound.api.dto.TokenResponseDTO;
import com.schedule.share.user.application.service.user.vo.TokenVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenDTOMapper {

    TokenResponseDTO.AccessAndRefreshToken toResponseDTO (TokenVO.AccessAndRefreshToken accessAndRefreshToken);
}
