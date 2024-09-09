package com.schedule.share.user.application.port.inbound;

import com.schedule.share.user.application.service.user.vo.FavoriteVO;

import java.util.List;

public interface FavoriteCommand {

    long create(long userId, FavoriteVO.save param);

    void delete(long userId, long id);

    void delete(long userId, List<Long> ids);
}
