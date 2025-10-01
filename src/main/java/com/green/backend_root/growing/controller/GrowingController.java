package com.green.backend_root.growing.controller;

import com.green.backend_root.growing.dto.GrowingDTO;
import com.green.backend_root.growing.service.GrowingService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/growings")
@RequiredArgsConstructor
public class GrowingController {
  private final GrowingService growingService;

  // 센서 데이터 상세 조회
  @GetMapping("")
  public List<GrowingDTO> getGrowingList() {
    return growingService.getGrowingList();
  }

  // 7일 간 센서 데이터 조회
  @GetMapping("/weekly")
  public ResponseEntity<?> getWeeklyGrowingList(){
    try {
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(growingService.getWeeklyGrowingList());
    } catch (Exception e) {
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("조회 중 서버 오류 발생");
    }
  }
}
