package com.schedule.share.user.domain.mapper;

import com.schedule.share.infra.rdb.entity.FavoriteEntity;
import com.schedule.share.user.application.service.user.vo.FavoriteVO;
import com.schedule.share.user.domain.Favorite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FavoriteMapper {

    @Mapping(source = "isAllday", target = "isAllday")
    Favorite favoriteVoSaveToDomain(FavoriteVO.save favorite);

    @Mapping(source = "allday", target = "isAllday")
    Favorite favoriteEntityToDomain(FavoriteEntity favoriteEntity);

    @Mapping(source = "allday", target = "isAllday")
    FavoriteEntity favoriteToEntity(Favorite favorite);

    @Mapping(source = "allday", target = "isAllday")
    FavoriteVO.Favorite favoriteToVO(Favorite favorite);
}
