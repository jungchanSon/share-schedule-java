package com.schedule.share.user.adaptor.outbound;

import com.schedule.share.infra.rdb.entity.UserEntity;
import com.schedule.share.infra.rdb.repository.UserRepository;
import com.schedule.share.user.domain.User;
import com.schedule.share.user.domain.mapper.UserMapper;
import com.schedule.share.user.domain.mapper.UserMapperImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({UserQueryAdaptor.class})
class UserQueryAdaptorTest {

    @Autowired
    private UserQueryAdaptor userQueryAdaptor;

    @Autowired
    private UserRepository userRepository;

    @Spy
    private UserMapper userMapper = new UserMapperImpl();

    @Test
    @Transactional
    void 모두조회() {
        UserEntity testUser1 = UserEntity.builder()
                .method("naver")
                .ci("test1")
                .nickname("testUser1")
                .build();

        UserEntity testUser2 = UserEntity.builder()
                .method("naver")
                .ci("test2")
                .nickname("testUser2")
                .build();

        userRepository.save(testUser1);
        userRepository.save(testUser2);

        List<User> list = userQueryAdaptor.list();

        assertTrue(list.size() >=  2);
    }

    @Test
    @Transactional
    void 단일조회() {
        UserEntity testUser1 = UserEntity.builder()
                .method("naver")
                .ci("test1")
                .nickname("testUser1")
                .build();

        UserEntity save = userRepository.save(testUser1);

        User user = userQueryAdaptor.get(save.getId());

        assertEquals(save.getId(), user.getId());
        assertEquals(save.getCi(), user.getCi());
        assertEquals(save.getNickname(), user.getNickname());
        assertEquals(save.getModifiedAt(), user.getModifiedAt());
        assertEquals(save.getRegisteredAt(), user.getRegisteredAt());
    }

    @Test
    @Transactional
    void Ci로_조회() {
        UserEntity testUser1 = UserEntity.builder()
                .method("naver")
                .ci("test1")
                .nickname("testUser1")
                .build();

        UserEntity save = userRepository.save(testUser1);

        User user = userQueryAdaptor.getByCi(save.getCi());

        assertEquals(save.getId(), user.getId());
        assertEquals(save.getCi(), user.getCi());
        assertEquals(save.getNickname(), user.getNickname());
        assertEquals(save.getModifiedAt(), user.getModifiedAt());
        assertEquals(save.getRegisteredAt(), user.getRegisteredAt());
    }
}