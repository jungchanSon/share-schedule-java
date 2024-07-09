package com.schedule.share.user.application.service;

import java.util.List;

public interface DomainQuery<T> {

    public T get(long id);

    public List<T> list();

}
