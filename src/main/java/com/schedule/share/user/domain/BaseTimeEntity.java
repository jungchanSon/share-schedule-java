package com.schedule.share.user.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 상속 시 이 클래스의 필드들도 칼럼으로 인식
@EntityListeners(AuditingEntityListener.class) // 시간측정 기능 추가
public abstract class BaseTimeEntity { // 인스턴스 생성 방지를 위해 추상클래스로 선언

    // 등록 일시
    @CreatedDate
    @Column(name = "registered_at")
    @NotNull
    private LocalDateTime registeredAt;

    // 수정 일시
    @LastModifiedDate // Entity 값 변경 시 시간 자동 저장
    @Column(name = "modified_at")
    @NotNull
    private LocalDateTime modifiedAt;

    // 해당 엔티티를 저장하기 이전에 실행
    @PrePersist
    public void onPrePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.registeredAt = now;
        this.modifiedAt = now;
    }

    // 해당 엔티티를 업데이트 하기 이전에 실행
    @PreUpdate
    public void onPreUpdate() {
        this.modifiedAt = LocalDateTime.now();
    }
}
