package com.schedule.share.user.adaptor.outbound;

import com.schedule.share.infra.rdb.entity.FavoriteEntity;
import com.schedule.share.infra.rdb.repository.FavoriteRepository;
import com.schedule.share.user.application.port.outbound.FavoriteQueryPort;
import com.schedule.share.user.domain.Favorite;
import com.schedule.share.user.domain.mapper.FavoriteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FavoriteQueryAdaptor implements FavoriteQueryPort {

    private final FavoriteRepository favoriteRepository;

    @Override
    public Favorite get(long id) {
        FavoriteEntity favoriteEntity = favoriteRepository.findById(id).orElseThrow();

        return FavoriteMapper.INSTANCE.favoriteEntityToDomain(favoriteEntity);
    }

    @Override
    public List<Favorite> list() {
        return favoriteRepository.findAll().stream().map(
                FavoriteMapper.INSTANCE::favoriteEntityToDomain
        ).toList();
    }
}