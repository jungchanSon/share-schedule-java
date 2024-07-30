package com.schedule.share.user.domain.mapper;

import com.schedule.share.infra.rdb.entity.RefreshTokenEntity;
import com.schedule.share.user.domain.RefreshToken;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface
RefreshTokenMapper {

    RefreshToken refreshTokenEntityToDomain (RefreshTokenEntity refreshTokenEntity);

    RefreshTokenEntity refreshTokenToEntity (RefreshToken refreshToken);
}