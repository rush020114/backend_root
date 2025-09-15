package com.green.backend_root.application.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApplicationDTO {
  private int applNum;
  private String applRole;
  private String businessTel;
  private String applAddr;
  private String addrDetail;
  private String content;
  private String userId;
  private String businessTelArr;
}
