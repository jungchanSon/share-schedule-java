package com.schedule.share.user.application.port.outbound;

import com.schedule.share.user.domain.User;

import java.util.List;

public interface UserQueryPort {

    public List<User> findAll();

    public User findById(Long id);
}