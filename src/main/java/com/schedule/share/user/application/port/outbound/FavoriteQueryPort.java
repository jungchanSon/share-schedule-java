package com.schedule.share.user.application.port.outbound;

import com.schedule.share.user.application.service.DomainQuery;
import com.schedule.share.user.domain.Favorite;

import java.util.List;

public interface FavoriteQueryPort extends DomainQuery<Favorite> {

    List<Favorite> list(long userId);

}
