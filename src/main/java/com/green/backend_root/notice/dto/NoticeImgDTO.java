package com.green.backend_root.notice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NoticeImgDTO {
  private int imgNum;               // 이미지 번호 (PK)
  private String originImgName;     // 원본 파일명
  private String attachedImgName;   // 첨부된 파일명 (UUID)
  private int noticeId;

  public NoticeImgDTO(){
  }
}