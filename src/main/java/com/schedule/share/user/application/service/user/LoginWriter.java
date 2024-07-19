package com.schedule.share.user.application.service.user;

import com.schedule.share.user.application.port.inbound.LoginCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginWriter implements LoginCommand {
}
