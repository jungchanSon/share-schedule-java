package com.schedule.share.user.application.service;

public interface DomainCommand<R> {
    public Long create(R param);
    public Long update(Long id, R param );
    public Long delete(Long id);
}