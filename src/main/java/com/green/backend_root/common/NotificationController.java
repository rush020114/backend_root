package com.green.backend_root.common;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class NotificationController {

  private final SimpMessagingTemplate messagingTemplate;

  // 관리자에게 알림
  public void notifyAdmin(String userId, String sensors) {
    // WebSocket으로 보낼 데이터 구성
    Map<String, Object> alertData = new HashMap<>();
    alertData.put("userId", userId);                           // 문의자 ID
    alertData.put("sensors", Arrays.asList(sensors.split(","))); // 센서 배열로 변환
    alertData.put("timestamp", LocalDateTime.now().toString());  // 현재 시간

    // "/topic/admin" 구독 중인 관리자에게 전송
    messagingTemplate.convertAndSend("/topic/admin", alertData);
  }

  // 특정 사용자에게 알림
  public void notifyUser(String userId, String message) {
    messagingTemplate.convertAndSend("/topic/user/" + userId, message);
  }
}