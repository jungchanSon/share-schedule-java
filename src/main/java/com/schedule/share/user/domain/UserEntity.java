package com.schedule.share.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schedule.share.common.enums.ProviderType;
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
@Table(name = "user")
public class UserEntity extends BaseTimeEntity {

    // 시퀀스
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long userId;

    // 사용자 CI
    @Column(name = "ci", length = 64, unique = true)
    @NotNull
    @Size(max = 64)
    private String ci;

//    // 사용자 email
//    @Column(name = "email", length = 128, unique = true)
//    @NotNull
//    @Size(max = 512)
//    private String email;
//
//    @Column(name = "email_verified_yn", length = 1)
//    @NotNull
//    @Size(min = 1, max = 1)
//    private String emailVerifiedYn;

    // 사용자 프로필
    @Column(name = "image", length = 50)
    @NotNull
    @Size(max = 128)
    private String image;

    // 사용자 닉네임
    @Size(max = 64)
    private String nickname;

    // 인증 제공자 타입
    @Column(name = "method", length = 16)
    @Enumerated(EnumType.STRING)
    private ProviderType method;

    public UserEntity(
            @NotNull @Size(max = 64) String ci,
            @NotNull @Size(max = 128) String nickname,
//            @NotNull @Size(max = 128) String email,
//            @NotNull @Size(max = 1) String emailVerifiedYn,
            @NotNull @Size(max = 128) String image,
            @NotNull ProviderType method
    ) {
        this.ci = ci;
        this.nickname = nickname;
//        this.email = email != null ? email : "NO_EMAIL";
//        this.emailVerifiedYn = emailVerifiedYn;
        this.image = image != null ? image : "";
        this.method = method;
    }

    // toString 메서드 추가
    @Override
    public String toString() {
        return "UserPrincipal{ci : " + ci + ", method : " + method;
    }
}