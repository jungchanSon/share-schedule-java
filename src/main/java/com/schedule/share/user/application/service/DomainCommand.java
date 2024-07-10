package com.schedule.share.user.application.service;

public interface DomainCommand<R> {

    long create(R param);

    void update(long id, R param);

    void delete(long id);

}
