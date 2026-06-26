package com.spring.miniguestbook.service;

import com.spring.miniguestbook.dto.CreateGuestbook;
import com.spring.miniguestbook.dto.GuestbookResponse;
import com.spring.miniguestbook.dto.UpdateGuestbook;
import com.spring.miniguestbook.entity.Guestbook;
import com.spring.miniguestbook.repository.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

// @Service : 비즈니스 로직을 담당하는 계층 (컨트롤러는 요청만 받고, 실제 일은 여기서)
@Service
// @RequiredArgsConstructor : final 필드(Repository)를 받는 생성자 자동 생성 → 의존성 주입
@RequiredArgsConstructor
public class GuestbookService {

    private final GuestbookRepository guestbookRepository;

    // 등록 : 요청 DTO → 엔티티로 만들어 저장하고, 저장 결과를 응답 DTO로 변환해 돌려줌
    // @Transactional : 메서드 전체를 하나의 트랜잭션으로 묶는다.
    //                  중간에 예외가 나면 DB 작업이 전부 취소(롤백)된다. : "다 되거나, 아예 안 되거나".
    @Transactional
    public GuestbookResponse create(CreateGuestbook createGuestbook) {
        // 1) 요청 DTO의 값으로 엔티티 생성
        Guestbook guestbook = new Guestbook(
                createGuestbook.getName(),
                createGuestbook.getMessage()
        );

        // 2) DB에 저장 (save가 id·생성일이 채워진 엔티티를 돌려줌)
        Guestbook savedGuestbook = guestbookRepository.save(guestbook);

        // 3) 엔티티 → 응답 DTO로 변환
        GuestbookResponse guestbookResponse = new GuestbookResponse(
                savedGuestbook.getId(),
                savedGuestbook.getName(),
                savedGuestbook.getMessage(),
                savedGuestbook.getCreatedAt(),
                savedGuestbook.getModifiedAt()
        );

        return guestbookResponse;
    }

    // 전체 조회 : 엔티티 목록을 하나씩 응답 DTO로 변환해 모음
    // @Transactional(readOnly = true) : 읽기 전용 트랜잭션
    //                  변경 감지 등 불필요한 작업을 줄여 조회 성능에 약간 이점이 있음. (조회는 readOnly로)
    @Transactional(readOnly = true)
    public List<GuestbookResponse> findAll() {
        List<GuestbookResponse> guestbookResponseList = new ArrayList<>();

        List<Guestbook> guestbookList = guestbookRepository.findAll();
        for (Guestbook guestbook : guestbookList) {
            GuestbookResponse guestbookResponse = new GuestbookResponse(
                    guestbook.getId(),
                    guestbook.getName(),
                    guestbook.getMessage(),
                    guestbook.getCreatedAt(),
                    guestbook.getModifiedAt()
            );

            guestbookResponseList.add(guestbookResponse);
        }

        return guestbookResponseList;
    }

    // 단건 조회 : id로 하나 찾아 응답 DTO로 변환 (없으면 getOrThrow가 404)
    // 조회만 하므로 읽기 전용 트랜잭션
    @Transactional(readOnly = true)
    public GuestbookResponse findOne(Long id) {
        Guestbook guestbook = getOrThrow(id);

        GuestbookResponse guestbookResponse = new GuestbookResponse(
                guestbook.getId(),
                guestbook.getName(),
                guestbook.getMessage(),
                guestbook.getCreatedAt(),
                guestbook.getModifiedAt()
        );

        return guestbookResponse;
    }

    // 수정 : 기존 글을 찾아 값만 바꾼 뒤 다시 저장
    // @Transactional : 조회 → 변경 → 저장을 한 묶음으로. 도중 실패 시 전부 롤백
    @Transactional
    public GuestbookResponse update(Long id, UpdateGuestbook updateGuestbook) {
        Guestbook guestbook = getOrThrow(id);

        // 엔티티의 update 메서드로 값 변경 (setter 대신 의도가 드러나는 메서드)
        guestbook.update(
                updateGuestbook.getName(),
                updateGuestbook.getMessage()
        );

        Guestbook updatedGuestbook = guestbookRepository.save(guestbook);

        GuestbookResponse guestbookResponse = new GuestbookResponse(
                updatedGuestbook.getId(),
                updatedGuestbook.getName(),
                updatedGuestbook.getMessage(),
                updatedGuestbook.getCreatedAt(),
                updatedGuestbook.getModifiedAt()
        );

        return guestbookResponse;
    }

    // 삭제 : 먼저 존재 여부를 확인(없으면 404)한 뒤 삭제
    // @Transactional : 조회와 삭제를 하나의 트랜잭션으로 묶음
    @Transactional
    public void delete(Long id) {
        Guestbook guestbook = getOrThrow(id);
        guestbookRepository.delete(guestbook);
    }

    // 내부 공통 메서드 : id로 엔티티를 찾고, 없으면 404(ResponseStatusException)를 던짐
    // private 이라 외부에서는 못 쓰고, 이 서비스 안에서 중복을 줄이는 용도!
    private Guestbook getOrThrow(Long id) {
        return guestbookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "방명록을 찾을 수 없어요: " + id));
    }

}
