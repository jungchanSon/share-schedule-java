package com.schedule.share.user.adaptor.outbound;

import com.schedule.share.infra.rdb.repository.FavoriteRepository;
import com.schedule.share.user.domain.Favorite;
import com.schedule.share.user.domain.mapper.FavoriteMapper;
import com.schedule.share.user.domain.mapper.FavoriteMapperImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FavoriteQueryAdaptorTest {

    @Autowired
    private FavoriteCommandAdaptor favoriteCommandAdaptor;

    @Autowired
    private FavoriteQueryAdaptor favoriteQueryAdaptor;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Spy
    private FavoriteMapper favoriteMapper = new FavoriteMapperImpl();

    List<Long> favoriteIdList;
    List<Favorite> favoriteList;

    @Test
    @Transactional
    void get() {
        for (int t=0; t<favoriteIdList.size(); t++) {
            Long id = favoriteIdList.get(t);

            Favorite findFavorite = favoriteQueryAdaptor.get(id);
            Favorite savedFavorite = favoriteList.get(t);

            assertEquals(savedFavorite.getUserId(), findFavorite.getUserId());
            assertEquals(savedFavorite.getScheduleId(), findFavorite.getScheduleId());
            assertEquals(savedFavorite.getCalendarId(), findFavorite.getCalendarId());
            assertEquals(savedFavorite.isAllday(), findFavorite.isAllday());
            assertEquals(savedFavorite.getScheduleStartDatetime(), findFavorite.getScheduleStartDatetime());
            assertEquals(savedFavorite.getScheduleEndDatetime(), findFavorite.getScheduleEndDatetime());
        }
    }

    @Test
    @Transactional
    void list() {
        List<Favorite> list = favoriteQueryAdaptor.list();
        assertTrue(list.size() >= 3);
    }

    @BeforeEach
    @Transactional
    void initBeforeTest() {
        System.out.println("beforeEach");
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now();

        Favorite favorite1 = Favorite.builder()
                .userId(1)
                .scheduleId(1)
                .calendarId(1)
                .isAllday(true)
                .scheduleStartDatetime(start)
                .scheduleEndDatetime(end)
                .build();
        Favorite favorite2 = Favorite.builder()
                .userId(1)
                .scheduleId(2)
                .calendarId(2)
                .isAllday(true)
                .scheduleStartDatetime(start)
                .scheduleEndDatetime(end)
                .build();
        Favorite favorite3 = Favorite.builder()
                .userId(1)
                .scheduleId(3)
                .calendarId(3)
                .isAllday(true)
                .scheduleStartDatetime(start)
                .scheduleEndDatetime(end)
                .build();

        long favoriteId1 = favoriteCommandAdaptor.create(favorite1);
        long favoriteId2 = favoriteCommandAdaptor.create(favorite2);
        long favoriteId3 = favoriteCommandAdaptor.create(favorite3);

        favoriteIdList = List.of(favoriteId1, favoriteId2, favoriteId3);
        favoriteList = List.of(favorite1, favorite2, favorite3);
    }
}