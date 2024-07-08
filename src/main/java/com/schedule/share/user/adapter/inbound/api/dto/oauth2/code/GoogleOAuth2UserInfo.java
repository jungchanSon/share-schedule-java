package com.schedule.share.user.adapter.inbound.api.dto.oauth2.code;

import com.schedule.share.user.adapter.inbound.api.dto.OAuth2UserInfo;

import java.util.Map;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo {

    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getCi() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getNickname() {
        return (String) attributes.get("name");
    }

    @Override
    public String getImage() {
        return (String) attributes.get("picture");
    }
}
