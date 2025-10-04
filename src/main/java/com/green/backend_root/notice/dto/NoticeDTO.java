package com.green.backend_root.notice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class NoticeDTO {
  private int noticeId;           // 공지 번호
  private String noticeTitle;     // 공지 제목
  private String noticeContent;   // 공지 내용
  private LocalDateTime noticeDate; // 공지 등록일
  private String userId;          // 작성자 ID
  private String isImportant;     // 중요 공지 여부 (Y/N)
  // 문의 상세 조회 시 이미지 등록 여부를 판단
  private int imgCnt;
  private List<NoticeImgDTO> noticeImgDTOList;
  private int[] noticeIdArr;
  private int[] imgNumArr;

  public NoticeDTO(){
  }
}
