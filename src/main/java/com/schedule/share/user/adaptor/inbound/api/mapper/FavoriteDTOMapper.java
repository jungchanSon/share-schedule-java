package com.schedule.share.user.adaptor.inbound.api.mapper;

import com.schedule.share.user.adaptor.inbound.api.dto.FavoriteRequestDTO;
import com.schedule.share.user.adaptor.inbound.api.dto.FavoriteResponseDTO;
import com.schedule.share.user.application.service.user.vo.FavoriteVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FavoriteDTOMapper {

    FavoriteDTOMapper INSTANCE = Mappers.getMapper(FavoriteDTOMapper.class);

    FavoriteVO.save toVo(FavoriteRequestDTO.Favorite favoriteRequestDTO);

    FavoriteResponseDTO.Response toResponseDTO(FavoriteVO.Favorite favorite);
}
