package com.schedule.share.user.application.service;

import java.util.List;

public interface DomainQuery<T> {

    T get(long id);

    List<T> list();

}
