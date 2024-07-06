package com.schedule.share.user.application.service.user;

import com.schedule.share.user.application.port.outbound.UserCommandPort;
import com.schedule.share.user.application.port.outbound.UserQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserService {
    private final UserQueryPort userQueryPort;
    private final UserCommandPort userCommandPort;
}
