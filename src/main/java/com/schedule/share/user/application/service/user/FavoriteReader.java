package com.schedule.share.user.application.service.user;

import com.schedule.share.infra.rdb.entity.FavoriteEntity;
import com.schedule.share.user.application.port.inbound.FavoriteQuery;
import com.schedule.share.user.application.port.outbound.FavoriteQueryPort;
import com.schedule.share.user.application.service.user.vo.FavoriteVO;
import com.schedule.share.user.domain.Favorite;
import com.schedule.share.user.domain.mapper.FavoriteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteReader implements FavoriteQuery {

    private final FavoriteQueryPort favoriteQueryPort;


    @Override
    public FavoriteVO.Favorite get(long id) {
        Favorite favorite = favoriteQueryPort.get(id);

        return FavoriteMapper.INSTANCE.favoriteToVO(favorite);
    }

    @Override
    public List<FavoriteVO.Favorite> list() {
        List<Favorite> list = favoriteQueryPort.list();

        return list.stream().map(FavoriteMapper.INSTANCE::favoriteToVO).toList();
    }
}
