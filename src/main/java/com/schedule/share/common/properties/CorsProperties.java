package com.schedule.share.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {
    // application.yml 의 cors
    private String allowedOrigins; // 허용된 출처목록
    private String allowedMethods; // 허용된 HTTP 메서드목록
    private String allowedHeaders; // 허용된 HTTP 헤더목록
    private long maxAge; // pre-flight 요청의 캐시 지속시간(초 단위)
}
