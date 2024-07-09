package com.schedule.share.user.adaptor.outbound;

import com.schedule.share.infra.rdb.entity.UserEntity;
import com.schedule.share.infra.rdb.repository.UserRepository;
import com.schedule.share.user.application.port.outbound.UserQueryPort;
import com.schedule.share.user.domain.User;
import com.schedule.share.user.domain.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class UserQueryAdaptor implements UserQueryPort {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User findById(Long id) {
        UserEntity user = userRepository.findById(id).get();

        return userMapper.userEntityToDomain(user);
    }

    @Override
    public List<User> findAll() {
        List<User> userList = userRepository.findAll().stream().map(
                userMapper::userEntityToDomain
        ).toList();

        return userList;
    }
}
