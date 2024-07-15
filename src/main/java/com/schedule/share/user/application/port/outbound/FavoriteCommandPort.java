package com.schedule.share.user.application.port.outbound;

import com.schedule.share.user.application.service.DomainCommand;
import com.schedule.share.user.domain.Favorite;

import java.util.List;

public interface FavoriteCommandPort extends DomainCommand<Favorite>{
    void delete(List<Long> ids);
}
