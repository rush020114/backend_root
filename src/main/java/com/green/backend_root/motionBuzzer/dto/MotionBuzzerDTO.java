package com.green.backend_root.motionBuzzer.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MotionBuzzerDTO {
  private int statusId;            // STATUS_ID (PK)
  private LocalDateTime timestamp; // TIMESTAMP (상태 변화 발생 시간)
  private boolean motionDetected;  // MOTION_DETECTED (0: OFF, 1: ON)

  // 통계용 필드 추가
  private Integer detectedCount;     // 감지 횟수
  private LocalDateTime lastDetected;// 최근 감지 시간

  private int fanMotor;
  private int waterPump;
  private int ledLight;
}
