package com.schedule.share.infra.rdb.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String method;

    private String ci;

    @Null
    @Nullable
    private String image;

    @CreatedDate
    private LocalDateTime registeredAt;

    @LastModifiedDate
    @Null
    @Nullable
    private LocalDateTime modifiedAt;

    public UserEntity(Long id,
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
        this.image = image;
        this.ci = ci;

        if (registeredAt == null)
            this.registeredAt = LocalDateTime.now();
        else
            this.registeredAt = registeredAt;

        if (modifiedAt == null)
            this.modifiedAt = LocalDateTime.now();
        else
            this.modifiedAt = modifiedAt;

    }
}