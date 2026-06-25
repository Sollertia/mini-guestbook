package com.spring.miniguestbook.repository;

import com.spring.miniguestbook.entity.Guestbook;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<엔티티, PK타입> 을 상속하면
// save·findAll·findById·delete 같은 기본 CRUD 메서드가 자동으로 생김
public interface GuestbookRepository extends JpaRepository<Guestbook, Long> {
}
