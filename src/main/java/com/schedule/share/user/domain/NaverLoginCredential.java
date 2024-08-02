package com.schedule.share.user.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NaverLoginCredential {
    private String code;
    private String state;
}
