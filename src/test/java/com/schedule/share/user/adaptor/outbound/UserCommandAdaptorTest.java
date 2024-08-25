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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserCommandAdaptorTest {

    @Autowired
    private UserCommandAdaptor userCommandAdaptor;

    @Autowired
    private UserRepository userRepository;

    @Spy
    private UserMapper userMapper = new UserMapperImpl();

    @Test
    @Transactional
    public void 한명저장() {
        User testUser = User.builder().method("naver")
                .image(new byte[]{1, 2, 3})
                .ci("testCi")
                .nickname("test")
                .build();

        long testUserId = userCommandAdaptor.create(testUser);

        UserEntity userEntity = userRepository.findById(testUserId).get();
        assertArrayEquals(testUser.getImage(), userEntity.getImage());
        assertEquals(testUserId, userEntity.getId());
        assertEquals(testUser.getCi(), userEntity.getCi());
        assertEquals(testUser.getNickname(), userEntity.getNickname());
    }

    @Test
    @Transactional
    public void 유저업데이트() {
        User testUser = User.builder().method("naver")
                .image(new byte[]{1, 2, 3})
                .ci("testCi")
                .nickname("test")
                .build();
        long testUserId = userCommandAdaptor.create(testUser);

        User newUser = User.builder().method("kakao")
                .image(new byte[]{2, 3, 4})
                .ci("newCi")
                .nickname("newTest")
                .build();
        newUser.updateRecentCalendarId(123);

        userCommandAdaptor.update(testUserId, newUser);

        UserEntity userEntity = userRepository.findById(testUserId).get();
        assertArrayEquals(userEntity.getImage(), new byte[]{2, 3, 4});
        assertEquals(userEntity.getNickname(), "newTest");
        assertEquals(userEntity.getRecentCalendarId(), 123);
    }
}