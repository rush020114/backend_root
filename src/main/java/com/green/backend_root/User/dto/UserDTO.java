package com.green.backend_root.User.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
  private String userId;                //아이디
  private String userPw;                //비밀번호
  private String userName;              //이름
  private String userTel;               //연락처
  private String userEmail;             //이메일
  private String userRole;              //사용자 유형
  private LocalDateTime createDate;     //가입일자

  //배열로 전달되는 연락처 저장할 변수
  private String[] userTelArr;
}
