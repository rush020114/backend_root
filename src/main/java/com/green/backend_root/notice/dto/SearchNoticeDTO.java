package com.green.backend_root.notice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
// 검색 데이터를 담기 위한 dto
public class SearchNoticeDTO {
  private String noticeId;
  private String noticeTitle;
  private String userId;
  private String noticeDate;

  public SearchNoticeDTO(){
  }
}
