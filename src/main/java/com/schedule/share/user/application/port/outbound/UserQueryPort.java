package com.schedule.share.user.application.port.outbound;

import com.schedule.share.user.application.service.DomainQuery;
import com.schedule.share.user.domain.User;

public interface UserQueryPort extends DomainQuery<User> {

    User getByCi(String ci);
}