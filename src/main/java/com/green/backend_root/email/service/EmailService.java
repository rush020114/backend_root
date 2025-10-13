package com.green.backend_root.email.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

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
    // 인증번호 생성 및 저장
    String code = generateAuthCode();
    authCodeMap.put(email, code);
    expireTimeMap.put(email, LocalDateTime.now().plusMinutes(5));         // 5분 후 만료

    try{
      // MimeMessage 생성 (HTML 이메일 지원)
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

      // 수신자 설정
      helper.setTo(email);

      // 제목 설정
      helper.setSubject("[회원가입] 이메일 인증번호");

      // HTML 본문 생성
      String htmlContent =
        "<!DOCTYPE html>" +
          "<html lang='ko'>" +
          "<head>" +
          "  <meta charset='UTF-8'>" +
          "  <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
          "</head>" +
          "<body style='margin: 0; padding: 0; font-family: \"Malgun Gothic\", \"맑은 고딕\", Arial, sans-serif;'>" +
          "  <div style='max-width: 550px; margin: 40px auto; background: white; border: 1px solid #e0e0e0; border-radius: 8px; overflow: hidden;'>" +
          "    " +
          "    <!-- 헤더 -->" +
          "    <div style='background: #2c3e50; padding: 30px; text-align: center;'>" +
          "      <h1 style='color: white; margin: 0; font-size: 22px; font-weight: 600; letter-spacing: 0.5px;'>" +
          "        이메일 인증" +
          "      </h1>" +
          "    </div>" +
          "    " +
          "    <!-- 본문 -->" +
          "    <div style='padding: 40px 35px;'>" +
          "      <p style='font-size: 15px; line-height: 1.8; color: #333; margin: 0 0 30px 0;'>" +
          "        다음 인증번호를 사용하여 회원가입을 완료하세요." +
          "      </p>" +
          "      " +
          "      <!-- 인증번호 타이틀 -->" +
          "      <div style='margin: 35px 0 15px 0;'>" +
          "        <p style='font-size: 14px; font-weight: bold; color: #666; margin: 0;'>" +
          "          인증번호" +
          "        </p>" +
          "      </div>" +
          "      " +
          "      <!-- 인증번호 박스 -->" +
          "      <div style='background: #f8f8f8; border: 1px solid #ddd; border-radius: 6px; padding: 30px; text-align: center; margin: 15px 0 35px 0;'>" +
          "        <span style='font-size: 40px; font-weight: bold; color: #2c3e50; letter-spacing: 10px; font-family: \"Courier New\", monospace;'>" +
          "          " + code + "" +
          "        </span>" +
          "      </div>" +
          "      " +
          "      <!-- 유의사항 타이틀 -->" +
          "      <div style='margin: 35px 0 15px 0;'>" +
          "        <p style='font-size: 14px; font-weight: bold; color: #666; margin: 0;'>" +
          "          유의사항" +
          "        </p>" +
          "      </div>" +
          "      " +
          "      <!-- 유의사항 박스 -->" +
          "      <div style='background: #f8f8f8; border: 1px solid #ddd; border-radius: 4px; padding: 20px 25px; margin: 15px 0;'>" +
          "        <p style='font-size: 14px; line-height: 1.9; color: #555; margin: 8px 0;'>" +
          "          ① 인증번호는 발급 후 <strong>5분간</strong> 유효합니다." +
          "        </p>" +
          "        <p style='font-size: 14px; line-height: 1.9; color: #555; margin: 8px 0;'>" +
          "          ② 본인이 요청하지 않은 경우 고객센터로 문의주시기 바랍니다." +
          "        </p>" +
          "        <p style='font-size: 14px; line-height: 1.9; color: #555; margin: 8px 0;'>" +
          "          ③ 인증번호는 타인에게 노출되지 않도록 주의하시기 바랍니다." +
          "        </p>" +
          "      </div>" +
          "    </div>" +
          "    " +
          "    <!-- 구분선 -->" +
          "    <div style='border-top: 1px solid #e0e0e0;'></div>" +
          "    " +
          "    <!-- 푸터 -->" +
          "    <div style='background: #fafafa; text-align: center; padding: 25px;'>" +
          "      <p style='font-size: 12px; color: #999; line-height: 1.6; margin: 3px 0;'>" +
          "        ※ 본 메일은 발신전용 메일입니다." +
          "      </p>" +
          "      <p style='font-size: 12px; color: #999; line-height: 1.6; margin: 3px 0;'>" +
          "        © 2025 root All rights reserved." +
          "      </p>" +
          "    </div>" +
          "  </div>" +
          "</body>" +
          "</html>";

      // HTML 형식으로 본문 설정 (두 번째 파라미터 true = HTML 모드)
      helper.setText(htmlContent, true);

      // 이메일 전송
      mailSender.send(message);

    } catch (Exception e){
      // 이메일 발송 실패 시 예외 처리
      throw new RuntimeException("이메일 발송 중 오류가 발생했습니다.", e);
    }
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

  // 이메일 회원 임시 비밀번호 발송
  public void sendUserPw(String email, String tempPw) throws MessagingException {
    // HTML 이메일 콘텐츠 생성
    String htmlContent =
      "<!DOCTYPE html>" +
        "<html lang='ko'>" +
        "<head>" +
        "  <meta charset='UTF-8'>" +
        "  <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
        "</head>" +
        "<body style='margin: 0; padding: 0; font-family: \"Malgun Gothic\", \"맑은 고딕\", Arial, sans-serif;'>" +
        "  <div style='max-width: 550px; margin: 40px auto; background: white; border: 1px solid #e0e0e0; border-radius: 8px; overflow: hidden;'>" +
        "    " +
        "    <!-- 헤더 -->" +
        "    <div style='background: #2c3e50; padding: 30px; text-align: center;'>" +
        "      <h1 style='color: white; margin: 0; font-size: 22px; font-weight: 600; letter-spacing: 0.5px;'>" +
        "        비밀번호 찾기" +
        "      </h1>" +
        "    </div>" +
        "    " +
        "    <!-- 본문 -->" +
        "    <div style='padding: 40px 35px;'>" +
        "      <p style='font-size: 15px; line-height: 1.8; color: #333; margin: 0 0 30px 0;'>" +
        "        임시 비밀번호가 발급되었습니다. 로그인 후 비밀번호를 변경해주세요." +
        "      </p>" +
        "      " +
        "      <!-- 임시 비밀번호 타이틀 -->" +
        "      <div style='margin: 35px 0 15px 0;'>" +
        "        <p style='font-size: 14px; font-weight: bold; color: #666; margin: 0;'>" +
        "          임시 비밀번호" +
        "        </p>" +
        "      </div>" +
        "      " +
        "      <!-- 임시 비밀번호 박스 -->" +
        "      <div style='background: #f8f8f8; border: 1px solid #ddd; border-radius: 6px; padding: 30px; text-align: center; margin: 15px 0 35px 0;'>" +
        "        <span style='font-size: 32px; font-weight: bold; color: #2c3e50; letter-spacing: 3px; font-family: \"Courier New\", monospace;'>" +
        "          " + tempPw + "" +
        "        </span>" +
        "      </div>" +
        "      " +
        "      <!-- 유의사항 타이틀 -->" +
        "      <div style='margin: 35px 0 15px 0;'>" +
        "        <p style='font-size: 14px; font-weight: bold; color: #666; margin: 0;'>" +
        "          유의사항" +
        "        </p>" +
        "      </div>" +
        "      " +
        "      <!-- 유의사항 박스 -->" +
        "      <div style='background: #f8f8f8; border: 1px solid #ddd; border-radius: 4px; padding: 20px 25px; margin: 15px 0;'>" +
        "        <p style='font-size: 14px; line-height: 1.9; color: #555; margin: 8px 0;'>" +
        "          ① 로그인 후 반드시 <strong>비밀번호를 변경</strong>하시기 바랍니다." +
        "        </p>" +
        "        <p style='font-size: 14px; line-height: 1.9; color: #555; margin: 8px 0;'>" +
        "          ② 본인이 요청하지 않은 경우 고객센터로 문의주시기 바랍니다." +
        "        </p>" +
        "        <p style='font-size: 14px; line-height: 1.9; color: #555; margin: 8px 0;'>" +
        "          ③ 임시 비밀번호는 타인에게 노출되지 않도록 주의하시기 바랍니다." +
        "        </p>" +
        "      </div>" +
        "    </div>" +
        "    " +
        "    <!-- 구분선 -->" +
        "    <div style='border-top: 1px solid #e0e0e0;'></div>" +
        "    " +
        "    <!-- 푸터 -->" +
        "    <div style='background: #fafafa; text-align: center; padding: 25px;'>" +
        "      <p style='font-size: 12px; color: #999; line-height: 1.6; margin: 3px 0;'>" +
        "        ※ 본 메일은 발신전용 메일입니다." +
        "      </p>" +
        "      <p style='font-size: 12px; color: #999; line-height: 1.6; margin: 3px 0;'>" +
        "        © 2025 root All rights reserved." +
        "      </p>" +
        "    </div>" +
        "  </div>" +
        "</body>" +
        "</html>";

    // MimeMessage 생성 (HTML 이메일 전송을 위해 필요)
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

    // 이메일 설정
    helper.setTo(email);                                    // 받는 사람
    helper.setSubject("[비밀번호 찾기] 임시 비밀번호 발급");    // 제목
    helper.setText(htmlContent, true);                      // HTML 형식으로 본문 설정 (true = HTML 모드)

    // 이메일 발송
    try {
      mailSender.send(message);
    } catch (Exception e) {
      throw new RuntimeException("임시 비밀번호 이메일 발송 중 오류가 발생했습니다.", e);
    }
  }
}
