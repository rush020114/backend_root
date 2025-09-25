package com.green.backend_root.application.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ApplicationDTO {
  private int applNum;
  private String farmName;
  private String applRole;
  private String businessTel;
  private String applAddr;
  private String addrDetail;
  private String applContent;
  private LocalDateTime regDate;
  private LocalDateTime apprDate;
  private String userId;
  private String businessTelArr;
}
