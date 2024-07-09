package com.schedule.share.user.application.service;

public interface DomainCommand<R> {

    public long create(R param);

    public void update(long id, R param);

    public void delete(long id);

}
