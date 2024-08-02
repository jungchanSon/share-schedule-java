package com.schedule.share.user.application.port.inbound;

import com.schedule.share.user.application.service.user.vo.FavoriteVO;

import java.util.List;

public interface FavoriteCommand {

    long create(FavoriteVO.save param);

    void delete(long id);

    void delete(List<Long> ids);
}
