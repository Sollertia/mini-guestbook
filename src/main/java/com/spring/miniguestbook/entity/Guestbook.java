package com.spring.miniguestbook.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
// @Entity : 이 클래스가 DB 테이블과 연결되는 객체라는 표시
@Entity
// @Table(name = ...) : 매핑될 실제 테이블 이름 지정 (생략하면 클래스명 사용)
@Table(name = "guestbooks")
// JPA는 기본 생성자가 필요하지만, 외부에서 함부로 빈 객체를 못 만들도록 PROTECTED로 막음
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Guestbook extends BaseEntity { // BaseEntity를 상속 → 생성일·수정일도 함께 가짐

    // @Id : 기본 키(PK) / @GeneratedValue(IDENTITY) : DB가 1씩 자동 증가시켜 줌
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column : 컬럼 제약 조건. nullable=false(필수), length(최대 길이)
    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 1000)
    private String message;

    // 값을 채워 새 글을 만들 때 쓰는 생성자 (setter 없이 생성 시점에만 값 주입)
    public Guestbook(String name, String message) {
        this.name = name;
        this.message = message;
    }

    // 수정용 메서드 : "무엇을 바꾸는지" 의도가 드러나게 엔티티 안에 둠 (setter 대신)
    public void update(String name, String message) {
        this.name = name;
        this.message = message;
    }

}
