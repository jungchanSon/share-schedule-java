package com.schedule.share.user.adaptor.outbound;

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
public class UserCommandAdapter implements UserCommandPort {

    private final UserRepository userRepository;

    @Override
    public long create(User user) {
        UserEntity userEntity = UserMapper.INSTANCE.userToEntity(user);

        return userRepository.save(userEntity).getId();
    }

    @Override
    @Transactional
    public void update(long id, User user) {
        UserEntity userEntity = userRepository.findById(id).get();

        UserEntity updatedUserEntity = userEntity.toBuilder()
                .nickname(user.getNickname())
                .method(user.getMethod())
                .ci(user.getCi())
                .image(user.getImage())
                .build();

        userRepository.save(updatedUserEntity);
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }
}
