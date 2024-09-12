package com.schedule.share.user.application.service.user;

import com.schedule.share.common.exception.Common403Exception;
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
    private final FavoriteMapper favoriteMapper;

    @Override
    public FavoriteVO.Favorite get(long userId, long id) {
        Favorite favorite = favoriteQueryPort.get(id);
        if(favorite.getUserId() != userId) throw new Common403Exception();

        return favoriteMapper.favoriteToVO(favorite);
    }

    @Override
    public List<FavoriteVO.Favorite> list(long userId) {
        List<Favorite> list = favoriteQueryPort.list(userId);

        return list.stream().map(favoriteMapper::favoriteToVO).toList();
    }

    @Override
    public FavoriteVO.Favorite get(long id) {
        return null;
    }

    @Override
    public List<FavoriteVO.Favorite> list() {
        return null;
    }
}
