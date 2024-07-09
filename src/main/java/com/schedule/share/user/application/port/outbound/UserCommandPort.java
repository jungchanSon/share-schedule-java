package com.schedule.share.user.application.port.outbound;

import com.schedule.share.user.domain.User;

public interface UserCommandPort {

    public long create(User user);

    public void update(User user);

    public void delete(long id);
}
