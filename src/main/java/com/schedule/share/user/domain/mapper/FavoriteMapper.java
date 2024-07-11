package com.schedule.share.user.domain.mapper;

import com.schedule.share.infra.rdb.entity.FavoriteEntity;
import com.schedule.share.user.application.service.user.vo.FavoriteVO;
import com.schedule.share.user.domain.Favorite;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FavoriteMapper {

    FavoriteMapper INSTANCE = Mappers.getMapper(FavoriteMapper.class);

    Favorite favoriteVoSaveToDomain(FavoriteVO.save favorite);

    Favorite favoriteEntityToDomain(FavoriteEntity favoriteEntity);

    FavoriteEntity favoriteToEntity(Favorite favorite);

    FavoriteVO.Favorite favoriteToVO(Favorite favorite);
}
