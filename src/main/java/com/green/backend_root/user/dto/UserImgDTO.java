package com.green.backend_root.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserImgDTO {
  private String userId;
  private String originImgName;
  private String attachedImgName;

  public UserImgDTO(){
  }
}
