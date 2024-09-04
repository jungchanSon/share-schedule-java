package com.schedule.share.infra.rdb.repository;

import com.schedule.share.infra.rdb.entity.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {

    List<FavoriteEntity> findAllByUserId(long userId);

}
