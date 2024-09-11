package com.schedule.share.user.application.port.inbound;

import com.schedule.share.user.application.service.DomainQuery;
import com.schedule.share.user.application.service.user.vo.FavoriteVO;

import java.util.List;

public interface FavoriteQuery extends DomainQuery<FavoriteVO.Favorite> {

    List<FavoriteVO.Favorite> list(long userId);
}
