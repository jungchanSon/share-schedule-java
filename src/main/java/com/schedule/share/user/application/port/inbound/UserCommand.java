package com.schedule.share.user.application.port.inbound;

import com.schedule.share.user.application.service.DomainCommand;
import com.schedule.share.user.application.service.user.vo.UserVO;

public interface UserCommand extends DomainCommand<UserVO.Save> {
    void updateCalendarId(long id, long recentCalendarId);
}
