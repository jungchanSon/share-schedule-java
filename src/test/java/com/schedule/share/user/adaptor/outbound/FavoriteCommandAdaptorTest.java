package com.schedule.share.user.adaptor.outbound;

import com.schedule.share.infra.rdb.entity.FavoriteEntity;
import com.schedule.share.infra.rdb.repository.FavoriteRepository;
import com.schedule.share.user.domain.Favorite;
import com.schedule.share.user.domain.mapper.FavoriteMapper;
import com.schedule.share.user.domain.mapper.FavoriteMapperImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FavoriteCommandAdaptorTest {

    @Autowired
    private FavoriteCommandAdaptor favoriteCommandAdaptor;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Spy
    private FavoriteMapper favoriteMapper = new FavoriteMapperImpl();

    private final long USERID1 = 10;
    private final long SCHEDULEID1 = 10;
    private final long CALENDARID1 = 10;

    private final long USERID2 = 11;
    private final long SCHEDULEID2 = 11;
    private final long CALENDARID2 = 11;

    private final long USERID3 = 12;
    private final long SCHEDULEID3 = 12;
    private final long CALENDARID3 = 12;

    private final LocalDateTime start = LocalDateTime.now();
    private final LocalDateTime end = LocalDateTime.now();
    @Test
    @Transactional
    @DisplayName("생성")
    void create() {

        Favorite favorite = Favorite.builder()
                .userId(USERID1)
                .scheduleId(SCHEDULEID1)
                .calendarId(CALENDARID1)
                .isAllday(true)
                .scheduleStartDatetime(start)
                .scheduleEndDatetime(end)
                .build();

        long id = favoriteCommandAdaptor.create(favorite);

        FavoriteEntity favoriteEntity = favoriteRepository.findById(id).get();

        assertEquals(favorite.getUserId(), favoriteEntity.getUserId());
        assertEquals(favorite.getScheduleId(), favoriteEntity.getScheduleId());
        assertEquals(favorite.getCalendarId(), favoriteEntity.getCalendarId());
        assertEquals(favorite.isAllday(), favoriteEntity.isAllday());
        assertEquals(favorite.getScheduleStartDatetime(), favoriteEntity.getScheduleStartDatetime());
        assertEquals(favorite.getScheduleEndDatetime(), favoriteEntity.getScheduleEndDatetime());
    }

    @Test
    @Transactional
    @DisplayName("단일 삭제")
    void delete() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now();

        Favorite favorite = Favorite.builder()
                .userId(USERID1)
                .scheduleId(SCHEDULEID1)
                .calendarId(CALENDARID1)
                .isAllday(true)
                .scheduleStartDatetime(start)
                .scheduleEndDatetime(end)
                .build();

        long favoriteIdForDelete = favoriteCommandAdaptor.create(favorite);

        favoriteCommandAdaptor.delete(favoriteIdForDelete);
        assertEquals(Optional.empty(), favoriteRepository.findById(favoriteIdForDelete));
    }

    @Test
    @DisplayName("벌크 삭제")
    void bulkDelete() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now();

        Favorite favorite1 = Favorite.builder()
                .userId(USERID1)
                .scheduleId(SCHEDULEID1)
                .calendarId(CALENDARID1)
                .isAllday(true)
                .scheduleStartDatetime(start)
                .scheduleEndDatetime(end)
                .build();
        Favorite favorite2 = Favorite.builder()
                .userId(USERID2)
                .scheduleId(SCHEDULEID2)
                .calendarId(CALENDARID2)
                .isAllday(true)
                .scheduleStartDatetime(start)
                .scheduleEndDatetime(end)
                .build();
        Favorite favorite3 = Favorite.builder()
                .userId(USERID3)
                .scheduleId(SCHEDULEID3)
                .calendarId(CALENDARID3)
                .isAllday(true)
                .scheduleStartDatetime(start)
                .scheduleEndDatetime(end)
                .build();

        long favoriteIdForDelete1 = favoriteCommandAdaptor.create(favorite1);
        long favoriteIdForDelete2 = favoriteCommandAdaptor.create(favorite2);
        long favoriteIdForDelete3 = favoriteCommandAdaptor.create(favorite3);
        List<Long> favoriteIdList = List.of(favoriteIdForDelete1, favoriteIdForDelete2, favoriteIdForDelete3);

        favoriteCommandAdaptor.delete(favoriteIdList);
        assertEquals(Optional.empty(), favoriteRepository.findById(favoriteIdForDelete1));
        assertEquals(Optional.empty(), favoriteRepository.findById(favoriteIdForDelete2));
        assertEquals(Optional.empty(), favoriteRepository.findById(favoriteIdForDelete3));
    }
}