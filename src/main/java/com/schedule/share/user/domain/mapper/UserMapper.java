package com.schedule.share.user.domain.mapper;

import com.schedule.share.infra.rdb.entity.UserEntity;
import com.schedule.share.user.application.service.user.vo.UserVO;
import com.schedule.share.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper{

    User userVoSaveToDomain (UserVO.Save userVoSave);

    User userEntityToDomain (UserEntity user);

    UserEntity userToEntity (User user);

    UserVO.User userToUserVO (User user);
}