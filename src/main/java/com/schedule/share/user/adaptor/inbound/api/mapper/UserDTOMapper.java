package com.schedule.share.user.adaptor.inbound.api.mapper;

import com.schedule.share.user.adaptor.inbound.api.dto.UserRequestDTO;
import com.schedule.share.user.adaptor.inbound.api.dto.UserResponseDTO;
import com.schedule.share.user.application.service.user.vo.UserVO;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {

    public UserVO.Save toVO(UserRequestDTO.User userRequestDTO) {
        return new UserVO.Save(
                userRequestDTO.nickname(),
                userRequestDTO.method(),
                userRequestDTO.ci(),
                userRequestDTO.image()
        );
    }

    public UserResponseDTO.Response toResponseDTO(UserVO.User userVO) {
        return new UserResponseDTO.Response(
                userVO.id(),
                userVO.nickname(),
                userVO.method(),
                userVO.ci(),
                userVO.image(),
                userVO.registeredAt(),
                userVO.modifiedAt()
        );
    }
}
