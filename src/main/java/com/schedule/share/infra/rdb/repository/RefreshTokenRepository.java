package com.schedule.share.infra.rdb.repository;

import com.schedule.share.infra.rdb.entity.RefreshTokenEntity;
import com.schedule.share.infra.rdb.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    RefreshTokenEntity findByUserId(long id);
}
