package com.schedule.share.user.adaptor.outbound;

import com.schedule.share.common.exception.Common404Exception;
import com.schedule.share.infra.rdb.entity.UserEntity;
import com.schedule.share.infra.rdb.repository.UserRepository;
import com.schedule.share.user.application.port.outbound.UserCommandPort;
import com.schedule.share.user.domain.User;
import com.schedule.share.user.domain.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Transactional
public class UserCommandAdaptor implements UserCommandPort {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public long create(User user) {
        UserEntity userEntity = userMapper.userToEntity(user);

        return userRepository.save(userEntity).getId();
    }

    @Override
    public void update(long id, User user) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(Common404Exception::new);

        userEntity.updateUserEntity(user);
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }
}
