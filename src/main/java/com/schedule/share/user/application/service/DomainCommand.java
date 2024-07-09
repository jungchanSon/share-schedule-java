package com.schedule.share.user.application.service;

public interface DomainCommand<R> {

    public Long create(R param);

    public void update(Long id, R param);

    public void delete(Long id);

}
