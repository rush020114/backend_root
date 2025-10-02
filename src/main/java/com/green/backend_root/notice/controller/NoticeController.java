package com.green.backend_root.notice.controller;

import com.green.backend_root.notice.dto.NoticeDTO;
import com.green.backend_root.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notices")
public class NoticeController {
  private final NoticeService noticeService;

  // 공지 등록 API
  @PostMapping("")
  public ResponseEntity<?> regNotice(@RequestBody NoticeDTO noticeDTO){
    try {
      noticeService.regNotice(noticeDTO);
      return ResponseEntity
              .status(HttpStatus.CREATED)
              .body("등록 성공");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("등록 중 서버 오류 발생");
    }
  }

  // 공지 목록 조회 API
  @GetMapping("")
  public ResponseEntity<?> getNoticeList() {
    try {
      List<NoticeDTO> noticeList = noticeService.getNoticeList();
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(noticeList);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("목록 조회 중 오류가 발생했습니다.");
    }
  }

  // 공지 상세 조회 API
  @GetMapping("/detail")
  public ResponseEntity<?> getNoticeDetail(NoticeDTO noticeDTO) {
    try {
      NoticeDTO notice = noticeService.getNoticeDetail(noticeDTO);
      if (notice == null) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("해당 공지사항을 찾을 수 없습니다.");
      }
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(notice);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("상세 조회 중 오류가 발생했습니다.");
    }
  }
  
  // 공지 수정 API
  @PutMapping("/{noticeId}")
  public ResponseEntity<?> updateNotice(
          @PathVariable("noticeId") int noticeId,
          @RequestBody NoticeDTO noticeDTO) {
    try {
      noticeDTO.setNoticeId(noticeId);
      noticeService.updateNotice(noticeDTO);
      return ResponseEntity
              .status(HttpStatus.OK)
              .body("수정 성공");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("수정 중 오류가 발생했습니다.");
    }
  }
}
