package com.schedule.share.user.application.service.user;

import com.schedule.share.common.exception.Common403Exception;
import com.schedule.share.user.application.port.outbound.FavoriteCommandPort;
import com.schedule.share.user.application.service.user.vo.FavoriteVO;
import com.schedule.share.user.domain.Favorite;
import com.schedule.share.user.domain.mapper.FavoriteMapper;
import com.schedule.share.user.domain.mapper.FavoriteMapperImpl;
import com.schedule.share.user.application.port.outbound.FavoriteQueryPort;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
class FavoriteWriterTest {

    @InjectMocks
    private FavoriteWriter favoriteWriter;

    @Mock
    private FavoriteCommandPort favoriteCommandPort;

    @Mock
    private FavoriteQueryPort favoriteQueryPort;

    @Spy
    private FavoriteMapper favoriteMapper = new FavoriteMapperImpl();

    @Test
    void create() {
        long userId = 10L;

        FavoriteVO.save favoriteVO = givenFavoriteVOWithId(userId);
        Favorite favorite = getFavoriteWithUserId(userId);

        long savedFavoriteId = favoriteWriter.create(userId, favoriteVO);

        then(favoriteCommandPort).should().create(eq(favorite));
        assertEquals(userId, savedFavoriteId);
    }

    @Test
    void delete_단일() {
        long userId = 10L;
        long favoriteId = 1L;

        //given
        Favorite favorite = Favorite.builder()
                .id(favoriteId)
                .userId(userId)
                .build();
        given(favoriteQueryPort.get(favoriteId)).willReturn(favorite);

        //when
        favoriteWriter.delete(userId, favoriteId);

        //then
        then(favoriteCommandPort).should().delete(favoriteId);
    }

    @Test
    void delete_복수() {
        long userId = 10L;
        long favoriteId1 = 1L;
        long favoriteId2 = 2L;
        long favoriteId3 = 3L;

        //given
        Favorite favorite1 = Favorite.builder()
                .id(favoriteId1)
                .userId(userId)
                .build();
        Favorite favorite2 = Favorite.builder()
                .id(favoriteId2)
                .userId(userId)
                .build();
        Favorite favorite3 = Favorite.builder()
                .id(favoriteId3)
                .userId(userId)
                .build();
        List<Long> favoriteIds = List.of(favoriteId1, favoriteId2, favoriteId3);

        given(favoriteQueryPort.get(favoriteId1)).willReturn(favorite1);
        given(favoriteQueryPort.get(favoriteId2)).willReturn(favorite2);
        given(favoriteQueryPort.get(favoriteId3)).willReturn(favorite3);

        //when
        favoriteWriter.delete(userId, favoriteIds);

        //then
        then(favoriteCommandPort).should().delete(favoriteIds);
    }

    @Test
    void exceptionDelete_다른_유저_요청에_대해_401에러() {
        long userId = 10L;
        long unauthorizedUserId = 11L;
        long favoriteId = 1L;

        //given
        Favorite favorite = Favorite.builder()
                .id(favoriteId)
                .userId(userId)
                .build();
        given(favoriteQueryPort.get(favoriteId)).willReturn(favorite);

        //when
        assertThrows(Common403Exception.class, () -> {
            favoriteWriter.delete(unauthorizedUserId, favoriteId);
        });

        //then
        then(favoriteCommandPort).shouldHaveNoInteractions();
    }

    @Test
    void exceptionBulkDelete_다른_유저_요청에_대해_401에러() {
        long userId = 10L;
        long unauthorizedUserId = 11L;
        long favoriteId1 = 1L;
        long favoriteId2 = 2L;
        long favoriteId3 = 3L;

        //given
        Favorite favorite1 = Favorite.builder()
                .id(favoriteId1)
                .userId(userId)
                .build();
        Favorite favorite2 = Favorite.builder()
                .id(favoriteId2)
                .userId(userId)
                .build();
        Favorite favorite3 = Favorite.builder()
                .id(favoriteId3)
                .userId(userId)
                .build();
        List<Long> favoriteIds = List.of(favoriteId1, favoriteId2, favoriteId3);

        given(favoriteQueryPort.get(favoriteId1)).willReturn(favorite1);
        given(favoriteQueryPort.get(favoriteId2)).willReturn(favorite2);
        given(favoriteQueryPort.get(favoriteId3)).willReturn(favorite3);

        //when
        assertThrows(Common403Exception.class, () -> {
            favoriteWriter.delete(unauthorizedUserId, favoriteIds);
        });

        //then
        then(favoriteCommandPort).shouldHaveNoInteractions();
    }

    private FavoriteVO.save givenFavoriteVOWithId(long userId) {
        FavoriteVO.save favoriteVO = mock(FavoriteVO.save.class);
        FavoriteVO.save save = favoriteVO.of(userId);

        Favorite favorite = favoriteMapper.favoriteVoSaveToDomain(save);

        given(favoriteCommandPort.create(favorite))
                .willReturn(userId);

        return favoriteVO;
    }

    private Favorite getFavoriteWithUserId(long userId) {
        FavoriteVO.save favoriteVO = mock(FavoriteVO.save.class);

        return favoriteMapper.favoriteVoSaveToDomain(favoriteVO.of(userId));
    }
}