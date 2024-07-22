package com.schedule.share.user.adaptor.outbound;

import com.schedule.share.user.application.port.outbound.LoginQueryPort;
import com.schedule.share.user.application.service.user.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class LoginNaverAdaptor implements LoginQueryPort<LoginVO.NaverOauthCredential> {

    @Value("${NAVER_CLIENT_ID}")
    private String NAVER_CLIENT_ID;
    @Value("${NAVER_CLIENT_SECRET}")
    private String NAVER_CLIENT_SECRET;
    @Value("${NAVER_ACCESS_TOKEN_URL}")
    private String NAVER_ACCESS_TOKEN_URL;
    @Value("${NAVER_USER_INFO_URL}")
    private String NAVER_USER_INFO_URL;

    private final WebClient webClient;

    @Override
    public String getCi(LoginVO.NaverOauthCredential oauthCredentials) {
        LoginVO.NaverAccessToken naverAccessToken = getNaverAccessToken(oauthCredentials);
        Mono<LoginVO.NaverCredential> authorization = webClient.post()
                .uri(NAVER_USER_INFO_URL)
                .header("Authorization", naverAccessToken.token_type() + " " + naverAccessToken.access_token())
                .retrieve()
                .bodyToMono(LoginVO.NaverCredential.class);
        LoginVO.NaverCredential block = authorization.block();

        return block.response().id();
    }

    private LoginVO.NaverAccessToken getNaverAccessToken(LoginVO.NaverOauthCredential accessTokenMaterial) {

        Mono<LoginVO.NaverAccessToken> accessTokenMono = webClient.post()
                .uri(NAVER_ACCESS_TOKEN_URL)
                .body(
                        BodyInserters.fromFormData("grant_type", "authorization_code")
                                .with("client_id", NAVER_CLIENT_ID)
                                .with("client_secret", NAVER_CLIENT_SECRET)
                                .with("code", accessTokenMaterial.code())
                                .with("state", accessTokenMaterial.state())
                )
                .header("Content-Type", "application/s-www-form-urlencoded;charset=utf-8")
                .retrieve()
                .bodyToMono(LoginVO.NaverAccessToken.class);

        return accessTokenMono.block();
    }
}
