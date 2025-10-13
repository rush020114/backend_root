package com.green.backend_root.config;

// cors 설정 클래스 파일 (cors : 서로 다른 웹끼리 데이터 통신 설정)
// - react, react native에서 spring 서버로 접근을 허용하는 설정
// - cors 설정은 기본적으로 spring 프로젝트(백엔드)에서 작업을 한다.

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// configuration 어노테이션 : 객체 생성 + 해당 클래스가 설정 파일임을 인지
// WebMvcConfigurer 인터페이스 안에 addCorsMappings 메서드 존재
// addCorsMappings메서드 안에서 cors 설정 코드 추가
@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**") // 접근 허용할 spring의 api url
            // 매개변수가 string...이다. ...을 쓰면 그 자료형은 원하는 만큼 넣을 수 있다.
            .allowedOrigins("http://localhost:5173", "http://192.168.30.79:5173") // 접근을 허용할 origin server
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowedHeaders("*") // 요청 시 허용할 헤더 정보
            .allowCredentials(false); // 토큰 로그인 방식 사용 시에는 true 설정
  }
}
