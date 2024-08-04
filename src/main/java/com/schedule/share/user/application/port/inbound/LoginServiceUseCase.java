package com.schedule.share.user.application.port.inbound;

import com.schedule.share.user.application.service.user.vo.SocialLoginVO;

public interface LoginServiceUseCase<C, U>{

    SocialLoginVO.Token sign(C naverOauthCredential, U userInfo);

    SocialLoginVO.Token login(C naverOauthCredential);
}
