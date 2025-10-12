package com.green.backend_root.question.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
// 검색 데이터를 담기 위한 dto
public class SearchQuestionDTO {
  private String qstId;
  private String qstStatus;
  private String qstType;
  private String qstTitle;
  private String userId;
  private String qstDate;
  private String userRole;

  public SearchQuestionDTO(){
  }
}
