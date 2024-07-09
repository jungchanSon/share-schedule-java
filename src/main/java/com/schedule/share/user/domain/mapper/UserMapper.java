package com.schedule.share.user.domain.mapper;

import com.schedule.share.infra.rdb.entity.UserEntity;
import com.schedule.share.user.application.service.user.vo.UserVO;
import com.schedule.share.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper{

    // id X
    public User userVoSaveToDomain (UserVO.Save userVoSave) {
        return new User(
            userVoSave.nickname(),
            userVoSave.method(),
            userVoSave.ci(),
            userVoSave.image()
        );
    }

    // id O
    public User userVoSaveToDomain (long id, UserVO.Save userVoSave) {
        return new User(
                id,
                userVoSave.nickname(),
                userVoSave.method(),
                userVoSave.ci(),
                userVoSave.image()
        );
    }

    public User userEntityToDomain (UserEntity user) {
        return new User(
                user.getId(),
                user.getNickname(),
                user.getMethod(),
                user.getCi(),
                user.getImage(),
                user.getRegisteredAt(),
                user.getModifiedAt()
        );
    }

    public UserEntity userToEntity (User user) {
        return new UserEntity(
                user.getId(),
                user.getNickname(),
                user.getMethod(),
                user.getCi(),
                user.getImage(),
                user.getRegisteredAt(),
                user.getModifiedAt()
        );
    }

    public UserVO.User userToUserVO (User user) {
        return new UserVO.User(
                user.getId(),
                user.getNickname(),
                user.getMethod(),
                user.getCi(),
                user.getImage(),
                user.getRegisteredAt(),
                user.getModifiedAt()
        );
    }
}