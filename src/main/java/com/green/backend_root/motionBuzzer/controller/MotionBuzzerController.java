package com.green.backend_root.motionBuzzer.controller;

import com.green.backend_root.motionBuzzer.dto.MotionBuzzerDTO;
import com.green.backend_root.motionBuzzer.service.MotionBuzzerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/motions")
@RequiredArgsConstructor
public class MotionBuzzerController {
  private final MotionBuzzerService motionBuzzerService;

  // 로그 목록 조회
  @GetMapping("")
  public List<MotionBuzzerDTO> getMotionBuzzerList() {
      return motionBuzzerService.getMotionBuzzerList();
  }

  // 감지 횟수 + 최근 감지 시간 조회
  @GetMapping("/stats")
  public MotionBuzzerDTO getMotionBuzzerStats() {
    return motionBuzzerService.getMotionBuzzerStats();
  }

}
