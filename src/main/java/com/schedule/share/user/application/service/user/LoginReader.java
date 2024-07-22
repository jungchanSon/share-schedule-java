package com.schedule.share.user.application.service.user;

import com.schedule.share.user.application.port.inbound.LoginQuery;
import com.schedule.share.user.application.port.outbound.LoginQueryPort;
import com.schedule.share.user.application.service.user.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginReader implements LoginQuery {

    private final LoginQueryPort loginQueryPort;
//
//    @Override
//    public String getCi(LoginVO.NaverOauthCredential naverOauthCredential) {
//        return loginQueryPort.getCi(naverOauthCredential);
//    }

    @Override
    public <T> String getCi(T naverOauthCredential) {
        return loginQueryPort.getCi(naverOauthCredential);
    }
}