package com.schedule.share.user.adaptor.inbound.api.mapper;

import com.schedule.share.user.adaptor.inbound.api.dto.UserRequestDTO;
import com.schedule.share.user.adaptor.inbound.api.dto.UserResponseDTO;
import com.schedule.share.user.application.service.user.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
public interface UserDTOMapper {

    UserDTOMapper INSTANCE = Mappers.getMapper(UserDTOMapper.class);

    UserVO.Save toVO(UserRequestDTO.User userRequestDTO);

    UserResponseDTO.Response toResponseDTO(UserVO.User userVO);
}
