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
    private final UserMapper userMapper;

    @Override
    public long create(UserVO.Save param) {
        long id = userCommandPort.create(userMapper.userVoSaveToDomain(param));

        return id;
    }

    @Override
    public void update(long id, UserVO.Save param) {
        userCommandPort.update(userMapper.userVoSaveToDomain(id, param));
    }

    @Override
    public void delete(long id) {
        userCommandPort.delete(id);
    }
}
