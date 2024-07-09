package com.schedule.share.user.adapter.inbound.api.dto;

import lombok.Getter;

import java.util.Map;

@Getter
public abstract class OAuth2UserInfo {
    // Map 에 OAuth2 인증 제공자가 반환한 사용자 정보 속성 저장
    protected Map<String, Object> attributes;

    // OAuth2 인증 제공자가 반환한 사용자 정보 속성 맵
    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    // 사용자 고유 ID(CI)를 반환
    public abstract String getCi();

    // 사용자 닉네임을 반환
    public abstract String getNickname();

    //public abstract String getEmail();

    // 사용자 프로필 이미지를 반환
    public abstract String getImage();
}
