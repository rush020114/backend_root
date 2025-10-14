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
      helper.setSubject("회원가입 이메일 인증");

      // HTML 본문 생성
      String htmlContent =
        "<!DOCTYPE html>" +
          "<html lang='ko'>" +
          "<head>" +
          "  <meta charset='UTF-8'>" +
          "  <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
          "</head>" +
          "<body style='margin: 0; padding: 0; font-family: Georgia, serif;'>" +
          "  <div style='background: #f5f1e8; max-width: 500px; margin: 0 auto;'>" +
          "    <div style='padding: 60px 40px 40px;'>" +
          "      " +
          "      <!-- 로고 텍스트 -->" +
          "      <div style='text-align: center; margin-bottom: 50px;'>" +
          "        <h1 style='font-size: 36px; font-weight: 300; color: #333; margin: 0; letter-spacing: 2px;'>root</h1>" +
          "      </div>" +
          "      " +
          "      <!-- 구분선 -->" +
          "      <div style='border-top: 1px solid #333; margin: 0 0 40px 0;'></div>" +
          "      " +
          "      <!-- 인사말 -->" +
          "      <p style='font-size: 13px; line-height: 1.9; color: #333; margin: 0 0 10px 0;'>" +
          "        회원가입을 완료하기 위한 인증번호가 발급되었습니다." +
          "      </p>" +
          "      " +
          "      <p style='font-size: 11px; line-height: 1.8; color: #666; margin: 0 0 40px 0;'>" +
          "        다음 인증번호를 입력하여 본인 인증을 완료해주시기 바랍니다." +
          "      </p>" +
          "      " +
          "      <!-- 인증번호 섹션 -->" +
          "      <div style='margin: 40px 0;'>" +
          "        <p style='font-size: 10px; letter-spacing: 2px; color: #999; margin: 0 0 15px 0; text-transform: uppercase;'>" +
          "          Authentication Code" +
          "        </p>" +
          "        <div style='border-top: 1px solid #d4d0c8; border-bottom: 1px solid #d4d0c8; padding: 25px 0; text-align: center;'>" +
          "          <span style='font-size: 32px; font-weight: 400; color: #333; letter-spacing: 12px; font-family: \"Courier New\", monospace;'>" +
          "            " + code + "" +
          "          </span>" +
          "        </div>" +
          "      </div>" +
          "      " +
          "      <!-- 유의사항 -->" +
          "      <div style='margin: 50px 0 40px;'>" +
          "        <p style='font-size: 10px; letter-spacing: 2px; color: #999; margin: 0 0 15px 0; text-transform: uppercase;'>" +
          "          Notice" +
          "        </p>" +
          "        <div style='border-top: 1px solid #e8e4dc; padding: 20px 0 0;'>" +
          "          <p style='font-size: 11px; line-height: 1.8; color: #666; margin: 0 0 12px 0;'>" +
          "            인증번호는 발급 후 5분간 유효합니다." +
          "          </p>" +
          "          <p style='font-size: 11px; line-height: 1.8; color: #666; margin: 0 0 12px 0;'>" +
          "            본인이 요청하지 않은 경우 고객센터로 문의주시기 바랍니다." +
          "          </p>" +
          "          <p style='font-size: 11px; line-height: 1.8; color: #666; margin: 0;'>" +
          "            인증번호는 타인에게 노출되지 않도록 주의하시기 바랍니다." +
          "          </p>" +
          "        </div>" +
          "      </div>" +
          "      " +
          "      <!-- 구분선 -->" +
          "      <div style='border-top: 1px solid #333; margin: 40px 0;'></div>" +
          "      " +
          "      <!-- 푸터 -->" +
          "      <div style='text-align: center;'>" +
          "        <p style='font-size: 9px; line-height: 1.7; color: #999; margin: 0 0 8px 0; letter-spacing: 0.5px;'>" +
          "          본 메일은 발신전용 메일입니다." +
          "        </p>" +
          "        <p style='font-size: 9px; line-height: 1.7; color: #999; margin: 0; letter-spacing: 0.5px;'>" +
          "          © 2025 root. All rights reserved." +
          "        </p>" +
          "      </div>" +
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
        "<body style='margin: 0; padding: 0; font-family: Georgia, serif;'>" +
        "  <div style='background: #f5f1e8; max-width: 500px; margin: 0 auto;'>" +
        "    <div style='padding: 60px 40px 40px;'>" +
        "      " +
        "      <!-- 로고 텍스트 -->" +
        "      <div style='text-align: center; margin-bottom: 50px;'>" +
        "        <h1 style='font-size: 36px; font-weight: 300; color: #333; margin: 0; letter-spacing: 2px;'>root</h1>" +
        "      </div>" +
        "      " +
        "      <!-- 구분선 -->" +
        "      <div style='border-top: 1px solid #333; margin: 0 0 40px 0;'></div>" +
        "      " +
        "      <!-- 인사말 -->" +
        "      <p style='font-size: 13px; line-height: 1.9; color: #333; margin: 0 0 10px 0;'>" +
        "        비밀번호 찾기를 통해 임시 비밀번호가 발급되었습니다." +
        "      </p>" +
        "      " +
        "      <p style='font-size: 11px; line-height: 1.8; color: #666; margin: 0 0 40px 0;'>" +
        "        다음 임시 비밀번호로 로그인 후 반드시 비밀번호를 변경해주시기 바랍니다." +
        "      </p>" +
        "      " +
        "      <!-- 임시 비밀번호 섹션 -->" +
        "      <div style='margin: 40px 0;'>" +
        "        <p style='font-size: 10px; letter-spacing: 2px; color: #999; margin: 0 0 15px 0; text-transform: uppercase;'>" +
        "          Temporary Password" +
        "        </p>" +
        "        <div style='border-top: 1px solid #d4d0c8; border-bottom: 1px solid #d4d0c8; padding: 25px 0; text-align: center;'>" +
        "          <span style='font-size: 32px; font-weight: 400; color: #333; letter-spacing: 12px; font-family: \"Courier New\", monospace;'>" +
        "            " + tempPw + "" +
        "          </span>" +
        "        </div>" +
        "      </div>" +
        "      " +
        "      <!-- 유의사항 -->" +
        "      <div style='margin: 50px 0 40px;'>" +
        "        <p style='font-size: 10px; letter-spacing: 2px; color: #999; margin: 0 0 15px 0; text-transform: uppercase;'>" +
        "          Notice" +
        "        </p>" +
        "        <div style='border-top: 1px solid #e8e4dc; padding: 20px 0 0;'>" +
        "          <p style='font-size: 11px; line-height: 1.8; color: #666; margin: 0 0 12px 0;'>" +
        "            로그인 후 반드시 비밀번호를 변경하시기 바랍니다." +
        "          </p>" +
        "          <p style='font-size: 11px; line-height: 1.8; color: #666; margin: 0 0 12px 0;'>" +
        "            본인이 요청하지 않은 경우 고객센터로 문의주시기 바랍니다." +
        "          </p>" +
        "          <p style='font-size: 11px; line-height: 1.8; color: #666; margin: 0;'>" +
        "            임시 비밀번호는 타인에게 노출되지 않도록 주의하시기 바랍니다." +
        "          </p>" +
        "        </div>" +
        "      </div>" +
        "      " +
        "      <!-- 구분선 -->" +
        "      <div style='border-top: 1px solid #333; margin: 40px 0;'></div>" +
        "      " +
        "      <!-- 푸터 -->" +
        "      <div style='text-align: center;'>" +
        "        <p style='font-size: 9px; line-height: 1.7; color: #999; margin: 0 0 8px 0; letter-spacing: 0.5px;'>" +
        "          본 메일은 발신전용 메일입니다." +
        "        </p>" +
        "        <p style='font-size: 9px; line-height: 1.7; color: #999; margin: 0; letter-spacing: 0.5px;'>" +
        "          © 2025 root. All rights reserved." +
        "        </p>" +
        "      </div>" +
        "    </div>" +
        "  </div>" +
        "</body>" +
        "</html>";

    // MimeMessage 생성 (HTML 이메일 전송을 위해 필요)
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

    // 이메일 설정
    helper.setTo(email);                                    // 받는 사람
    helper.setSubject("임시 비밀번호 발급");    // 제목
    helper.setText(htmlContent, true);                      // HTML 형식으로 본문 설정 (true = HTML 모드)

    // 이메일 발송
    try {
      mailSender.send(message);
    } catch (Exception e) {
      throw new RuntimeException("임시 비밀번호 이메일 발송 중 오류가 발생했습니다.", e);
    }
  }
}