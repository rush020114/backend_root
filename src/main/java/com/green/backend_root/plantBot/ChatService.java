package com.green.backend_root.plantBot;
import com.green.backend_root.plantBot.ChatMessage;
import com.green.backend_root.plantBot.PlantResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service

public class ChatService {
  // 메모리에 채팅 메시지 저장 (실제로는 DB 사용)
  private final Map<String, List<ChatMessage>> chatSessions = new HashMap<>();

  public String createSession() {
    String sessionId = UUID.randomUUID().toString();
    List<ChatMessage> messages = new ArrayList<>();

    // 웰컴 메시지
    messages.add(new ChatMessage("bot", "안녕하세요! 식물 사진을 보내주시면 분석해드려요! 🔍"));

    chatSessions.put(sessionId, messages);
    return sessionId;
  }

  public List<ChatMessage> getMessages(String sessionId) {
    return chatSessions.getOrDefault(sessionId, new ArrayList<>());
  }

  public void addMessage(String sessionId, ChatMessage message) {
    chatSessions.computeIfAbsent(sessionId, k -> new ArrayList<>()).add(message);
  }

  public String processTextMessage(String sessionId, String content) {
    // 사용자 메시지 추가
    addMessage(sessionId, new ChatMessage("user", content));

    // 봇 응답 생성
    String botResponse = generateTextResponse(content);
    addMessage(sessionId, new ChatMessage("bot", botResponse));

    return botResponse;
  }

  public String processPlantImage(String sessionId, List<PlantResult> results) {
    if (results.isEmpty()) {
      return "🤔 죄송해요, 이 식물을 인식하지 못했어요. 다른 각도나 더 선명한 사진으로 다시 시도해주세요!";
    }

    StringBuilder response = new StringBuilder();
    response.append("🌿 <strong>식물 인식 결과</strong><br><br>");

    for (int i = 0; i < results.size(); i++) {
      PlantResult plant = results.get(i);
      response.append(String.format(
              "<div style=\"background: #f0f8f0; border: 1px solid #4caf50; border-radius: 8px; padding: 10px; margin: 8px 0;\">" +
                      "<div style=\"float: right; background: #4caf50; color: white; padding: 2px 8px; border-radius: 12px; font-size: 12px;\">%d%%</div>" +
                      "<strong>%d. %s</strong><br>" +
                      "<span style=\"color: #666;\">일반명: %s</span><br>" +
                      "<span style=\"color: #666;\">과: %s</span>" +
                      "</div>",
              plant.getConfidence(),
              i + 1,
              plant.getScientificName(),
              plant.getCommonName(),
              plant.getFamily()
      ));
    }

    response.append("<br>💡 더 궁금한 게 있으면 물어보세요!");

    return response.toString();
  }

  private String generateTextResponse(String message) {
    String lowerMessage = message.toLowerCase();

    if (lowerMessage.contains("안녕") || lowerMessage.contains("하이")) {
      return "안녕하세요! 저는 식물 인식 전문 AI예요. 식물 사진을 보내주시면 종류를 알려드릴게요! 🌿";
    } else if (lowerMessage.contains("도움") || lowerMessage.contains("사용법")) {
      return "📷 사진 버튼을 눌러서 식물 사진을 업로드하시면 됩니다!<br><br>" +
              "🔍 AI가 사진을 분석해서 식물의 종류, 학명, 일반명을 알려드려요.<br><br>" +
              "💡 잎, 꽃, 열매가 잘 보이는 선명한 사진일수록 정확해요!";
    } else if (lowerMessage.contains("고마워") || lowerMessage.contains("감사")) {
      return "천만에요! 언제든 식물에 대해 궁금한 게 있으면 물어보세요! 🌿😊";
    } else {
      return "흥미로운 질문이네요! 하지만 저는 식물 인식 전문이에요. " +
              "📷 사진 버튼을 눌러서 식물 사진을 올려주시면 정확한 정보를 알려드릴게요! 🌱";
    }
  }
}




