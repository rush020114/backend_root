package com.green.backend_root.email.controller;

import com.green.backend_root.email.dto.EmailSendDTO;
import com.green.backend_root.email.dto.EmailVerifyDTO;
import com.green.backend_root.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {
  private final EmailService emailService;

  // 이메일 인증번호 전송 API
  @PostMapping("/send")
  public ResponseEntity<?> sendAuthCode(@RequestBody EmailSendDTO sendDTO){
    try {
      emailService.sendAuthCode(sendDTO.getUserEmail());
      return ResponseEntity
              .status(HttpStatus.OK)
              .body("인증번호가 전송되었습니다.");
    }catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("인증번호 전송 중 오류가 발생했습니다.");
    }
  }

  // 인증번호 검증 API
  @PostMapping("/verify")
  public ResponseEntity<?> verifyAuthCode(@RequestBody EmailVerifyDTO verifyDTO){
    try {
      boolean isValid = emailService.verifyAuthCode(verifyDTO.getUserEmail(), verifyDTO.getAuthCode());
      if(isValid){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("인증이 완료되었습니다.");
      }
      else{
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("인증번호가 일치하지 않거나 만료되었습니다.");
      }
    } catch (Exception e){
      e.printStackTrace();
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("인증번호 검증 중 오류가 발생했습니다.");
    }
  }
}
