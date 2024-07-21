package com.schedule.share.user.application.port.outbound;

import com.schedule.share.user.domain.Favorite;

import java.util.List;

public interface FavoriteCommandPort {

    long create(Favorite param);

    void delete(long id);

    void delete(List<Long> ids);
}
