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
    public Long create(UserVO.Save param) {
        Long id = userCommandPort.create(userMapper.userVoSaveToDomain(param));

        return id;
    }

    @Override
    public void update(Long id, UserVO.Save param) {
        userCommandPort.update(userMapper.userVoSaveToDomain(id, param));
    }

    @Override
    public void delete(Long id) {
        userCommandPort.delete(id);
    }
}
