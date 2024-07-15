package com.schedule.share.user.application.port.inbound;

import com.schedule.share.user.application.service.DomainCommand;
import com.schedule.share.user.application.service.user.vo.FavoriteVO;

import java.util.List;

public interface FavoriteCommand extends DomainCommand<FavoriteVO.save> {
    void delete(List<Long> ids);
}
