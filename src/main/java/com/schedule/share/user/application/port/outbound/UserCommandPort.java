package com.schedule.share.user.application.port.outbound;

import com.schedule.share.user.domain.User;

public interface UserCommandPort {

    long create(User user);

    void update(User user);

    void delete(long id);
}
