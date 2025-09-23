package com.green.backend_root.answer.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class AnswerDTO {
  private int ansId;
  private String ansContent;
  private int qstId;
  private String userId;
  private LocalDateTime ansDate;

  public AnswerDTO(){
  }
}
