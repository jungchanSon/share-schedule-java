package com.schedule.share.user.adaptor.outbound;

import com.schedule.share.infra.rdb.entity.FavoriteEntity;
import com.schedule.share.infra.rdb.repository.FavoriteRepository;
import com.schedule.share.user.application.port.outbound.FavoriteCommandPort;
import com.schedule.share.user.domain.Favorite;
import com.schedule.share.user.domain.mapper.FavoriteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class FavoriteCommandAdaptor implements FavoriteCommandPort {

    private final FavoriteRepository favoriteRepository;

    @Override
    public long create(Favorite param) {
        FavoriteEntity favoriteEntity = FavoriteMapper.INSTANCE.favoriteToEntity(param);

        return favoriteRepository.save(favoriteEntity).getId();
    }

    @Override
    public void update(long id, Favorite param) {
        FavoriteEntity result = FavoriteMapper.INSTANCE.favoriteToEntity(param).toBuilder()
                .userId(param.getUserId())
                .scheduleId(param.getScheduleId())
                .calendarId(param.getCalendarId())
                .isAllday(param.getIsAllday())
                .scheduleStartDatetime(param.getScheduleStartDatetime())
                .scheduleEndDatetime(param.getScheduleEndDatetime())
                .build();

        favoriteRepository.save(result);
    }

    @Override
    public void delete(long id) {
        favoriteRepository.deleteById(id);
    }
}
