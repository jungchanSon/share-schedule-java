package com.schedule.share.infra.repository;

import com.schedule.share.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // CI(고객 식별자)로 사용자 찾기
    UserEntity findByCi(String ci);
}
