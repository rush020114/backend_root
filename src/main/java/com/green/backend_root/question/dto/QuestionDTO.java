package com.green.backend_root.question.dto;

import com.green.backend_root.user.dto.UserDTO;
import com.green.backend_root.application.dto.ApplicationDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class QuestionDTO {
  private int qstId;
  private String qstTitle;
  private String qstContent;
  private String userId;
  private LocalDateTime qstDate;
  private String qstStatus;
  private String qstType;
  private String alertSensors;
  private List<QuestionImgDTO> questionImgDTOList;
  private String userRole;
  // 문의 상세 조회 시 이미지 등록 여부를 판단
  private int imgCnt;
  private UserDTO userDTO;

  public QuestionDTO(){
  }
}
