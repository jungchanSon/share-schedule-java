package com.schedule.share.user.application.port.outbound;

import com.schedule.share.user.domain.User;

import java.util.List;

public interface UserQueryPort {

    List<User> findAll();

    User findById(long id);
}