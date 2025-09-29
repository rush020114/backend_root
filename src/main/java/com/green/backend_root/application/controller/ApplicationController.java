package com.green.backend_root.application.controller;

import com.green.backend_root.application.dto.ApplicationDTO;
import com.green.backend_root.application.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationController {
  private final ApplicationService applicationService;

  // 서비스 신청 api
  @PostMapping("")
  public ResponseEntity<?> regService(@RequestBody ApplicationDTO applicationDTO){
    try {
      applicationService.regService(applicationDTO);
      return ResponseEntity
              .status(HttpStatus.CREATED)
              .body("신청 접수");
    } catch (Exception e) {
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("신청 중 서버 오류 발생");
    }
  }

  // 서비스 기본 정보 조회 api
  @GetMapping("/{userId}")
  public ResponseEntity<?> getServiceInfo(@PathVariable("userId") String userId){
    try {
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(applicationService.getServiceInfo(userId));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("조회 중 서버 오류 발생");
    }
  }
}
