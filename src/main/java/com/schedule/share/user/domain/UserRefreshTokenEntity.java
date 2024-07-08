package com.schedule.share.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_refresh_token")
public class UserRefreshTokenEntity extends BaseTimeEntity {

    // 리프레시 토큰 시퀀스
    @JsonIgnore
    @Id
    @Column(name = "refresh_token_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refreshTokenSeq;

    // 사용자 CI
    @Column(name = "ci", length = 64, unique = true)
    @NotNull
    @Size(max = 64)
    private String ci;

    // 리프레시 토큰
    @Column(name = "refresh_token", length = 256)
    @NotNull
    @Size(max = 256)
    private String refreshToken;

    public UserRefreshTokenEntity(
            @NotNull @Size(max = 64) String ci,
            @NotNull @Size(max = 256) String refreshToken
    ) {
        this.ci = ci;
        this.refreshToken = refreshToken;
    }
}
