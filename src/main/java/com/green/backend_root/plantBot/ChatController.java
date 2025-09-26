package com.green.backend_root.plantBot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")  // 개발용 - 모든 도메인 허용
public class ChatController {

  @Autowired
  private ChatService chatService;

  @Autowired
  private PlantService plantService;

  @PostMapping("/session")
  public ResponseEntity<Map<String, String>> createSession() {
    String sessionId = chatService.createSession();

    Map<String, String> response = new HashMap<>();
    response.put("sessionId", sessionId);

    return ResponseEntity.ok(response);
  }

  // 경로 수정: /chat/ 제거
  @GetMapping("/session/{sessionId}/messages")
  public ResponseEntity<List<ChatMessage>> getMessages(@PathVariable String sessionId) {
    List<ChatMessage> messages = chatService.getMessages(sessionId);
    return ResponseEntity.ok(messages);
  }

  // 경로 수정: /chat/ 제거
  @PostMapping("/session/{sessionId}/message")
  public ResponseEntity<ChatMessage> sendMessage(
          @PathVariable String sessionId,
          @RequestBody MessageRequest request) {

    String botResponse = chatService.processTextMessage(sessionId, request.getContent());

    ChatMessage response = new ChatMessage("bot", botResponse);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/session/{sessionId}/image")
  public ResponseEntity<ChatMessage> uploadImage(
          @PathVariable String sessionId,
          @RequestParam("image") MultipartFile imageFile) {

    try {
      // API 키 검증 활성화
      if (!plantService.isApiKeyValid()) {
        ChatMessage error = new ChatMessage("bot", "❌ PlantNet API 키가 설정되지 않았습니다.");
        return ResponseEntity.ok(error);
      }

      // 파일 검증
      if (imageFile.isEmpty() || !imageFile.getContentType().startsWith("image/")) {
        ChatMessage error = new ChatMessage("bot", "❌ 올바른 이미지 파일을 업로드해주세요.");
        return ResponseEntity.ok(error);
      }

      // 사용자 메시지 추가 (이미지)
      ChatMessage userMessage = new ChatMessage("user", "이미지 업로드");
      chatService.addMessage(sessionId, userMessage);

      // 식물 인식
      List<PlantResult> results = plantService.identifyPlant(imageFile);

      // 결과 메시지 생성
      String botResponse = chatService.processPlantImage(sessionId, results);
      ChatMessage response = new ChatMessage("bot", botResponse);
      chatService.addMessage(sessionId, response);

      return ResponseEntity.ok(response);

    } catch (Exception e) {
      e.printStackTrace(); // 디버깅용
      ChatMessage error = new ChatMessage("bot", "❌ 이미지 처리 중 오류가 발생했습니다: " + e.getMessage());
      return ResponseEntity.ok(error);
    }
  }

  @GetMapping("/health")
  public ResponseEntity<Map<String, Object>> health() {
    Map<String, Object> status = new HashMap<>();
    status.put("status", "OK");
    status.put("apiConfigured", plantService.isApiKeyValid());

    return ResponseEntity.ok(status);
  }
}
