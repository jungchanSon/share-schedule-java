package com.schedule.share.user.application.port.inbound;

import com.schedule.share.user.application.service.user.vo.LoginVO;

public interface LoginQuery {
    String getNaverCi(LoginVO.NaverOauthCredential naverOauthCredential);
}
