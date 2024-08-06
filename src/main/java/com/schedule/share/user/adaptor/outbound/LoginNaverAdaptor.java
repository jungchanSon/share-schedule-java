package com.schedule.share.user.adaptor.outbound;

import com.schedule.share.user.application.port.outbound.SocialLoginPort;
import com.schedule.share.user.application.service.user.vo.SocialLoginVO;
import com.schedule.share.user.domain.NaverLoginCredential;
import io.netty.handler.timeout.ReadTimeoutException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.http.HttpTimeoutException;

@Component
@RequiredArgsConstructor
public class LoginNaverAdaptor implements SocialLoginPort<NaverLoginCredential> {

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
    public String getCi(NaverLoginCredential oauthCredentials) {

        SocialLoginVO.NaverAccessToken naverAccessToken = getNaverAccessToken(oauthCredentials);

        Mono<SocialLoginVO.NaverCredential> authorization = webClient.post()
                .uri(NAVER_USER_INFO_URL)
                .header("Authorization", naverAccessToken.token_type() + " " + naverAccessToken.access_token())
                .retrieve()
                .bodyToMono(SocialLoginVO.NaverCredential.class)
                .onErrorMap(ReadTimeoutException.class, e ->
                        new HttpTimeoutException("[Time Out 예외] 네이버 CI"));

        SocialLoginVO.NaverCredential naverCredential = authorization.block();

        return naverCredential.response().id();
    }

    private SocialLoginVO.NaverAccessToken getNaverAccessToken(NaverLoginCredential accessTokenMaterial) {

        Mono<SocialLoginVO.NaverAccessToken> accessTokenMono = webClient.post()
                .uri(NAVER_ACCESS_TOKEN_URL)
                .body(BodyInserters.fromFormData("grant_type", "authorization_code")
                                .with("client_id", NAVER_CLIENT_ID)
                                .with("client_secret", NAVER_CLIENT_SECRET)
                                .with("code", accessTokenMaterial.getCode())
                                .with("state", accessTokenMaterial.getState()))
                .header("Content-Type", "application/s-www-form-urlencoded;charset=utf-8")
                .retrieve()
                .bodyToMono(SocialLoginVO.NaverAccessToken.class)
                .onErrorMap(ReadTimeoutException.class, e ->
                        new HttpTimeoutException("[Time Out 예외] 네이버 AccessToken"));
        ;

        return accessTokenMono.block();
    }
}
