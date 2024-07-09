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
    private final UserMapper userMapper;

    @Override
    public UserVO.User get(Long id) {
        UserVO.User user = userMapper.userToUserVO(userQueryPort.findById(id));

        return user;
    }

    @Override
    public List<UserVO.User> list() {
        List<UserVO.User> userList = userQueryPort.findAll().stream().map(userMapper::userToUserVO).toList();

        return userList;
    }
}
