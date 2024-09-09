package com.schedule.share.user.application.port.outbound;

import com.schedule.share.user.domain.Favorite;

import java.util.List;

public interface FavoriteCommandPort {

    long create(long userId, Favorite param);

    void delete(long userId, long id);

    void delete(long userId, List<Long> ids);
}
