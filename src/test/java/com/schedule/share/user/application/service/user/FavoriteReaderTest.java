package com.schedule.share.user.application.service.user;

import com.schedule.share.user.application.port.outbound.FavoriteQueryPort;
import com.schedule.share.user.application.service.user.vo.FavoriteVO;
import com.schedule.share.user.domain.Favorite;
import com.schedule.share.user.domain.mapper.FavoriteMapper;
import com.schedule.share.user.domain.mapper.FavoriteMapperImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class FavoriteReaderTest {

    @InjectMocks
    FavoriteReader favoriteReader;

    @Mock
    private FavoriteQueryPort favoriteQueryPort;

    @Spy
    private FavoriteMapper favoriteMapper = new FavoriteMapperImpl();

    @Test
    void get() {
        long userId = 10L;
        long favoriteId = 11L;
        Favorite build = Favorite.builder()
                .userId(userId)
                .id(favoriteId)
                .build();

        given(favoriteQueryPort.get(favoriteId)).willReturn(build);

        FavoriteVO.Favorite favorite = favoriteReader.get(userId, favoriteId);

        then(favoriteQueryPort).should().get(favoriteId);
        assertEquals(userId, favorite.userId());
        assertEquals(favoriteId, favorite.id());
    }

    @Test
    void list() {
        long userId = 1L;
        long favoriteId1 = 11L;
        long favoriteId2 = 12L;
        long favoriteId3 = 13L;

        Favorite build1 = Favorite.builder()
                .userId(userId)
                .id(favoriteId1)
                .build();
        Favorite build2 = Favorite.builder()
                .userId(userId)
                .id(favoriteId2)
                .build();
        Favorite build3 = Favorite.builder()
                .userId(userId)
                .id(favoriteId3)
                .build();
        List<Favorite> favoriteList = List.of(build1, build2, build3);
        given(favoriteQueryPort.list(userId)).willReturn(favoriteList);

        List<FavoriteVO.Favorite> list = favoriteReader.list(userId);

        assertEquals(3, list.size());
        then(favoriteQueryPort).should().list(userId);

    }
}