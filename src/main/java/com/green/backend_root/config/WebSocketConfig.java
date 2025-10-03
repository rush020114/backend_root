package com.green.backend_root.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker  // WebSocket 메시지 브로커 활성화
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    // 클라이언트가 구독할 경로 설정 (서버 → 클라이언트)
    config.enableSimpleBroker("/topic");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    // WebSocket 연결 엔드포인트 설정
    registry.addEndpoint("/ws")  // ws://localhost:8080/ws 로 연결
            .setAllowedOriginPatterns("*")  // 모든 origin 허용 (학원 내부망 포함)
            .withSockJS();  // SockJS 지원 (WebSocket 미지원 브라우저 대응)
  }
}