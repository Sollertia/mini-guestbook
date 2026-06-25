package com.spring.miniguestbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// [화면용 컨트롤러] 데이터 API(GuestbookController)와는 별개입니다!
// @Controller  → 뷰(HTML 화면) 이름을 반환  (데이터용 @RestController 와 헷갈리지 마세요!)
@Controller
public class GuestbookViewController {

    @GetMapping("/")
    public String home() {
        return "index"; // 문자열 "index" → templates/index.html 을 찾아 브라우저에 보여줍니다.
    }
}
