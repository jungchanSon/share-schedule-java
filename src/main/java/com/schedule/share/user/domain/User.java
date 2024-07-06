package com.schedule.share.user.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class User {
    private Long id;
    private String nickname;
    private String method;
    private String ci;
    private String image;
    private LocalDateTime registeredAt;
    private LocalDateTime modifiedAt;


//  ToDomain
    public User (
        Long id,
        String nickname,
        String method,
        String ci,
        String image,
        LocalDateTime registeredAt,
        LocalDateTime modifiedAt
    ) {
        this.id = id;
        this.nickname = nickname;
        this.method = method;
        this.ci = ci;
        this.image = image;
        this.registeredAt = registeredAt;
        this.modifiedAt = modifiedAt;

    }

    public User(String nickname, String method, String ci, String image) {
        this.nickname = nickname;
        this.method = method;
        this.ci = ci;
        this.image = image;
    }

    public User(Long id,
                String nickname,
                String method,
                String ci,
                String image
    ) {
        this.id = id;
        this.nickname = nickname;
        this.method = method;
        this.ci = ci;
        this.image = image;
    }
}
