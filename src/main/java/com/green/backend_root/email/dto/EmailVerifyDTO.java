package com.green.backend_root.email.dto;

import lombok.Data;

@Data
public class EmailVerifyDTO {
  private String userEmail;             // 이메일
  private String authCode;             // 인증번호
}
