package com.schedule.share.user.application.service.user;

import com.schedule.share.user.application.port.outbound.FavoriteCommandPort;
import com.schedule.share.user.application.port.outbound.FavoriteQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteQueryPort favoriteQueryPort;
    private final FavoriteCommandPort favoriteCommandPort;
}
