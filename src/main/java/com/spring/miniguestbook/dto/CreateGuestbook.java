package com.spring.miniguestbook.dto;

import lombok.Getter;

// [요청 DTO] 등록(POST) 요청의 JSON 본문을 담는 그릇
// @Getter : Jackson이 JSON을 이 객체로 바꿀 때 필드를 인식하고, 서비스가 값을 꺼낼 때 사용
@Getter
public class CreateGuestbook {
    private String name;
    private String message;
}
