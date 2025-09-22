package com.green.backend_root.User.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
  private String userId;
  private String userPw;
  private String userName;
  private String userTel;
  private String userEmail;
  private String userRole;
  private LocalDateTime createDate;
}
