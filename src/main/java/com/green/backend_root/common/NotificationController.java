package com.green.backend_root.common;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class NotificationController {

  private final SimpMessagingTemplate messagingTemplate;

  // 관리자에게 알림
  public void notifyAdmin(String message) {
    // 관리자에게 보낼 메시지를 client로 전달하는 메서드
    messagingTemplate.convertAndSend("/topic/admin", message);
  }

  // 특정 사용자에게 알림
  public void notifyUser(String userId, String message) {
    messagingTemplate.convertAndSend("/topic/user/" + userId, message);
  }
}