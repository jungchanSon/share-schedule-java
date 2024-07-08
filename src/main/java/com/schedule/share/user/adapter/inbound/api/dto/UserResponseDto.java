package com.schedule.share.user.adapter.inbound.api.dto;


import com.schedule.share.user.domain.UserEntity;
import lombok.*;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserResponseDto implements Serializable {
    private String ci;
    private String nickname;
    private String image;
    private String method;

    public UserResponseDto(UserEntity user) {
        this.ci = user.getCi();
        this.nickname = user.getNickname();
        this.image = user.getImage();
        this.method = user.getMethod().name();
    }
}