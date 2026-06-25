package com.spring.miniguestbook.dto;

import lombok.Getter;

// [요청 DTO] 수정(PUT) 요청으로 들어오는 "바꿀 값"을 담음
@Getter
public class UpdateGuestbook {
    private String name;
    private String message;
}
