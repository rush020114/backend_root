package com.green.backend_root.notice.mapper;

import com.green.backend_root.notice.dto.NoticeDTO;
import com.green.backend_root.notice.dto.NoticeImgDTO;
import com.green.backend_root.notice.dto.SearchNoticeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {
    // 공지 등록 시 공지 번호 조회 쿼리 실행하는 메서드
    int getNoticeId();
    
    // 공지 등록 쿼리 실행하는 메서드
    void regNotice(NoticeDTO noticeDTO);

    // 공지 이미지 등록
    void regNoticeImgs(List<NoticeImgDTO> noticeImgDTOList);

    // 공지 상세 조회 시 이미지 등록 여부 판단
    int getImgCnt(NoticeDTO noticeDTO);
    
    // 공지 목록 조회 쿼리 실행하는 메서드
    List<NoticeDTO> getNoticeList(SearchNoticeDTO searchNoticeDTO);
    
    // 공지 상세 조회 쿼리 실행하는 메서드
    NoticeDTO getNoticeDetail(NoticeDTO noticeDTO);
    
    // 공지 수정 쿼리 실행하는 메서드
    void updateNotice(NoticeDTO noticeDTO);

    // 공지 삭제
    void delNotice(int noticeId);

    // 공지 이미지 조회
    List<String> getNoticeImgList(int noticeId);
}
