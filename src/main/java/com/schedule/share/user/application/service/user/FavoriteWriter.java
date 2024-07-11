package com.schedule.share.user.application.service.user;

import com.schedule.share.user.application.port.inbound.FavoriteCommand;
import com.schedule.share.user.application.port.outbound.FavoriteCommandPort;
import com.schedule.share.user.application.service.user.vo.FavoriteVO;
import com.schedule.share.user.domain.mapper.FavoriteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteWriter implements FavoriteCommand {

    private final FavoriteCommandPort favoriteCommandPort;

    @Override
    public long create(FavoriteVO.save param) {
        return favoriteCommandPort.create(FavoriteMapper.INSTANCE.favoriteVoSaveToDomain(param));
    }

    @Override
    public void update(long id, FavoriteVO.save param) {
        favoriteCommandPort.update(id, FavoriteMapper.INSTANCE.favoriteVoSaveToDomain(param));
    }

    @Override
    public void delete(long id) {
        favoriteCommandPort.delete(id);
    }
}
