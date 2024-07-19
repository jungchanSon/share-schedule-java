package com.schedule.share.user.application.port.outbound;

import com.schedule.share.user.application.service.user.vo.LoginVO;

public interface LoginQueryPort {
    String getNaverCi(LoginVO.NaverOauthCredential oauthCredentials);
}
