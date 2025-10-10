package com.green.backend_root.email.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {
  private final JavaMailSender mailSender;

  // 인증번호 저장
  private Map<String, String> authCodeMap = new HashMap<>();
  // 인증 완료 여부
  private Map<String, Boolean> emailVerifiedMap = new HashMap<>();
  // 만료 시간 저장
  private Map<String, LocalDateTime> expireTimeMap = new HashMap<>();

  // 6자리 랜덤 인증번호 생성 메서드
  private String generateAuthCode(){
    Random random = new Random();
    int code = random.nextInt(900000) + 100000;         // 100000 ~ 999999
    return String.valueOf(code);
  }

  // 이메일로 인증번호 전송 메서드
  public void sendAuthCode(String email){
    String code = generateAuthCode();
    authCodeMap.put(email, code);
    expireTimeMap.put(email, LocalDateTime.now().plusMinutes(5));         // 5분 후 만료

    // 테스트용: 콘솔에 인증번호 출력
    System.out.println("==================");
    System.out.println("이메일: " + email);
    System.out.println("인증번호: " + code);
    System.out.println("==================");

//    SimpleMailMessage message = new SimpleMailMessage();
//    message.setTo(email);                                                 // 받는 사람
//    message.setSubject("[회원가입] 이메일 인증번호 : [" + code + "]");                           // 제목
//    message.setText("다음 인증번호를 사용하여 회원가입을 완료하세요.\n[" + code + "]\n5분 후 만료됩니다.");     // 내용
//
//    mailSender.send(message);                                            // 이메일 전송
  }

  // 인증번호 검증 메서드
  public boolean verifyAuthCode(String email, String authCode){
    // 시간 확인
    LocalDateTime expireTime = expireTimeMap.get(email);
    if(expireTime == null || LocalDateTime.now().isAfter(expireTime)){
      return false;         // 만료됨
    }
    // 인증번호 확인
    String savedCode = authCodeMap.get(email);
    if(authCode.equals(savedCode)){
      emailVerifiedMap.put(email, true);         // 인증 완료 상태 저장
      return true;
    }
    return false;
  }

  // 이메일 인증 완료 여부 확인 메서드
  public boolean isEmailVerified(String email){
    Boolean isVerified = emailVerifiedMap.get(email);
    return isVerified != null && isVerified;         // null이면 false 반환
  }
}
