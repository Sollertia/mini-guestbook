package com.spring.miniguestbook.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// 여러 엔티티가 공통으로 쓰는 "생성일·수정일"을 모아둔 부모 클래스
@Getter
// @MappedSuperclass : 이 클래스는 테이블이 아니라, 필드만 자식 엔티티 테이블에 내려보냄
@MappedSuperclass
// @EntityListeners(...) : 저장/수정 시점을 감지해 아래 날짜 필드를 자동으로 채워줌
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    // @CreatedDate : 처음 저장될 때 시각이 자동 입력됨
    // @Column(updatable = false) : 한 번 정해지면 수정되지 않도록 막음
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // @LastModifiedDate : 수정될 때마다 시각이 자동 갱신
    @LastModifiedDate
    private LocalDateTime modifiedAt;
}
