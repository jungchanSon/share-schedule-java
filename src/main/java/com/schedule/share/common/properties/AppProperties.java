package com.schedule.share.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;
/*
* AppProperties
*/
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    // application.yml 의 auth
    private final Auth auth = new Auth();
    private final OAuth2 oauth2 = new OAuth2();

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Auth {
        private String tokenSecret; //토큰키
        private long tokenExpiry; //토큰 만료시간
        private long refreshTokenExpiry; //리프레시 토큰 만료시간
    }

    @Getter
    public static final class OAuth2 {
        // 허용된 리다이렉트 URI 목록
        private List<String> authorizedRedirectUris = new ArrayList<>();

        public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
            this.authorizedRedirectUris = authorizedRedirectUris;
            return this;
        }
    }
}
