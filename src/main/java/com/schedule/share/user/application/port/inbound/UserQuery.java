package com.schedule.share.user.application.port.inbound;

import com.schedule.share.user.application.service.DomainQuery;
import com.schedule.share.user.application.service.user.vo.UserVO;

public interface UserQuery extends DomainQuery<UserVO.User> {
    long getRecentCalendarId(long id);
}
