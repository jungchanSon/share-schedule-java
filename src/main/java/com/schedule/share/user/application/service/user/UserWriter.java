package com.schedule.share.user.application.service.user;

import com.schedule.share.user.application.port.inbound.UserCommand;
import com.schedule.share.user.application.port.outbound.UserCommandPort;
import com.schedule.share.user.application.service.user.vo.UserVO;
import com.schedule.share.user.domain.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserWriter implements UserCommand {

    private final UserCommandPort userCommandPort;

    @Override
    public long create(UserVO.Save param) {
        return userCommandPort.create(UserMapper.INSTANCE.userVoSaveToDomain(param));
    }

    @Override
    public void update(long id, UserVO.Save param) {
        userCommandPort.update(id, UserMapper.INSTANCE.userVoSaveToDomain(param));
    }

    @Override
    public void delete(long id) {
        userCommandPort.delete(id);
    }
}
