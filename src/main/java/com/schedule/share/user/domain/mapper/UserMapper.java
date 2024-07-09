package com.schedule.share.user.domain.mapper;

import com.schedule.share.infra.rdb.entity.UserEntity;
import com.schedule.share.user.application.service.user.vo.UserVO;
import com.schedule.share.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper{

    UserMapper INSTANCE =  Mappers.getMapper(UserMapper.class);
    // id X
    User userVoSaveToDomain (UserVO.Save userVoSave);

    User userEntityToDomain (UserEntity user);

    UserEntity userToEntity (User user);

    UserVO.User userToUserVO (User user);
}