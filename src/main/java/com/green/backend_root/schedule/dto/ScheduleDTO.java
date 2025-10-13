package com.green.backend_root.schedule.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ScheduleDTO {
  private int schedId;
  private String userId;
  private String title;
  private String schedDate;  // "2025-10-09" 형식
  private String schedContent;
  private String category;
  private String isCompleted;
  private String createDate;

  public ScheduleDTO(){
  }
}
