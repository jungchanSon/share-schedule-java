package com.schedule.share.user.application.service;

import java.util.List;

public interface DomainQuery<R> {

    public R get(Long id);

    public List<R> list();
}
