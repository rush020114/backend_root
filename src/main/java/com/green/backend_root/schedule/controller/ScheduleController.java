package com.green.backend_root.schedule.controller;

import com.green.backend_root.schedule.dto.ScheduleDTO;
import com.green.backend_root.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

  private final ScheduleService scheduleService;

  // 일정 등록
  @PostMapping("")
  public ResponseEntity<?> regSchedule(@RequestBody ScheduleDTO scheduleDTO) {
    try {
      scheduleService.regSchedule(scheduleDTO);
      return ResponseEntity
              .status(HttpStatus.CREATED)
              .body("일정이 등록되었습니다.");
    } catch (Exception e) {
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("등록 중 서버 오류 발생");
    }
  }

  // 일정 목록 조회
  @GetMapping("/{userId}")
  public ResponseEntity<?> getScheduleList(@PathVariable("userId") String userId) {
    try {
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(scheduleService.getScheduleList(userId));
    } catch (Exception e) {
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("조회 중 서버 오류 발생");
    }
  }

  // 일정 상세 조회
  @GetMapping("detail/{schedId}")
  public ResponseEntity<?> getSchedule(@PathVariable("schedId") int schedId) {
    try {
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(scheduleService.getSchedule(schedId));
    } catch (Exception e) {
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("조회 중 서버 오류 발생");
    }
  }

  // 일정 수정
  @PutMapping("")
  public ResponseEntity<?> updateSchedule(@RequestBody ScheduleDTO scheduleDTO) {
    try {
      scheduleService.updateSchedule(scheduleDTO);
      return ResponseEntity
              .status(HttpStatus.OK)
              .body("일정이 수정되었습니다.");
    } catch (Exception e) {
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("수정 중 서버 오류 발생");
    }
  }

  // 일정 삭제
  @DeleteMapping("")
  public ResponseEntity<?> deleteSchedule(@RequestParam int schedId) {
    try {
      scheduleService.deleteSchedule(schedId);
      return ResponseEntity
              .status(HttpStatus.OK)
              .body("일정이 삭제되었습니다.");
    } catch (Exception e) {
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("삭제 중 서버 오류 발생");
    }
  }

  // 완료 처리
  @PutMapping("/complete")
  public ResponseEntity<?> updateCompleted(@RequestBody ScheduleDTO scheduleDTO) {
    try {
      scheduleService.updateCompleted(scheduleDTO);
      return ResponseEntity
              .status(HttpStatus.OK)
              .body("완료 처리되었습니다.");
    } catch (Exception e) {
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("처리 중 서버 오류 발생");
    }
  }
}
