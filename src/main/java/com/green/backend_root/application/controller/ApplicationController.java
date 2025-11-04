package com.green.backend_root.application.controller;

import com.green.backend_root.application.dto.ApplicationDTO;
import com.green.backend_root.application.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

  // 서비스 신청 현황 조회 api
  @GetMapping("/list")
  public List<ApplicationDTO> getApplicationList(
          @RequestParam(required = false) String userName
  ) {
    if (userName != null && !userName.isEmpty()) {
      return applicationService.searchByName(userName);
    }
    return applicationService.getApplicationList();
  }

  // 서비스 승인 처리 api
  @PutMapping("/approve/{applNum}")
  public ResponseEntity<?> approveApplication(@PathVariable("applNum") int applNum) {
    try {
      applicationService.approveApplication(applNum);
      return ResponseEntity.ok("승인 완료");
    } catch (Exception e) {
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("승인 중 오류 발생");
    }
  }

  // 회원명으로 검색
  @GetMapping("/appList/{userName}")
  public List<ApplicationDTO> searchByName(@PathVariable("userName") String userName) {
    return applicationService.searchByName(userName);
  }

  // 총 이용일 조회 api
  @GetMapping("/total-day/{userId}")
  public ResponseEntity<?> getTotalDays(@PathVariable("userId") String userId){
    try {
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(applicationService.getTotalDays(userId));
    } catch (Exception e) {
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("이용일 조회 중 서버 오류 발생");
    }
  }

  // 농장 정보 수정 api
  @PutMapping("")
  public ResponseEntity<?> updateFarmInfo(@RequestBody ApplicationDTO applicationDTO){
    try {
      applicationService.updateFarmInfo(applicationDTO);
      return ResponseEntity
              .status(HttpStatus.OK)
              .body("수정 완료");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("수정 중 서버 오류 발생");
    }
  }
}
