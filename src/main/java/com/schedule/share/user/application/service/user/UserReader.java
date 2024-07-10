package com.schedule.share.user.application.service.user;

import com.schedule.share.user.application.port.inbound.UserQuery;
import com.schedule.share.user.application.port.outbound.UserQueryPort;
import com.schedule.share.user.application.service.user.vo.UserVO;
import com.schedule.share.user.domain.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserReader implements UserQuery {

    private final UserQueryPort userQueryPort;

    @Override
    public UserVO.User get(long id) {
        return UserMapper.INSTANCE.userToUserVO(userQueryPort.get(id));
    }

    @Override
    public List<UserVO.User> list() {
        return userQueryPort.list().stream().map(UserMapper.INSTANCE::userToUserVO).toList();
    }
}