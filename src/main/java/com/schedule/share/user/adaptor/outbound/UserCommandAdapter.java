package com.schedule.share.user.adaptor.outbound;

import com.schedule.share.infra.rdb.repository.UserRepository;
import com.schedule.share.user.application.port.outbound.UserCommandPort;
import com.schedule.share.user.domain.User;
import com.schedule.share.user.domain.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCommandAdapter implements UserCommandPort {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Long create(User user) {
        return userRepository.save(userMapper.userToEntity(user)).getId();
    }

    @Override
    public void update(User user) {

        userRepository.save(userMapper.userToEntity(user));
    }

    @Override
    public void delete(Long id) {

        userRepository.deleteById(id);
    }
}
