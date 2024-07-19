package com.schedule.share.user.application.service.user;

import com.schedule.share.user.application.port.outbound.LoginCommandPort;
import com.schedule.share.user.application.port.outbound.LoginQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final LoginQueryPort loginQueryPort;
    private final LoginCommandPort loginCommandPort;
}
