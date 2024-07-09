package com.schedule.share.infra.repository;

import com.schedule.share.user.domain.UserRefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshTokenEntity, Long> {
    // CI(고객 식별자)로 리프레시 토큰을 찾기
    UserRefreshTokenEntity findByCi(String ci);
    // 리프레시 토큰 값으로 리프레시 토큰을 찾기
    UserRefreshTokenEntity findByRefreshToken(String refreshToken);
    // 리프레시 토큰 값으로 리프레시 토큰을 삭제
    void deleteByRefreshToken(String refreshToken);
}
