package com.green.backend_root.notice.service;

import com.green.backend_root.notice.dto.NoticeDTO;
import com.green.backend_root.notice.dto.NoticeImgDTO;
import com.green.backend_root.notice.mapper.NoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeMapper noticeMapper;
    
    // 공지 등록 기능 구현 메서드
    @Transactional(rollbackFor = Exception.class)
    public void regNotice(List<NoticeImgDTO> noticeImgDTOList, NoticeDTO noticeDTO) {
      // 공지 번호 조회 및 설정
      int nextNoticeId = noticeMapper.getNoticeId();
      noticeDTO.setNoticeId(nextNoticeId);

      // 공지 등록
      noticeMapper.regNotice(noticeDTO);

      // 공지 이미지 등록
      if(!noticeImgDTOList.isEmpty()){
        for(NoticeImgDTO img : noticeImgDTOList){
            img.setNoticeId(nextNoticeId);
        }
        noticeMapper.regNoticeImgs(noticeImgDTOList);
      }
    }
    
    // 공지 목록 조회 기능 구현 메서드
    public List<NoticeDTO> getNoticeList() {
      return noticeMapper.getNoticeList();
    }
    
    // 공지 상세 조회 기능 구현 메서드
    public NoticeDTO getNoticeDetail(NoticeDTO noticeDTO) {

      // 공지 상세 조회 시 이미지 등록 여부를 판단
      noticeDTO.setImgCnt(noticeMapper.getImgCnt(noticeDTO));
      // 공지 상세 조회
      return noticeMapper.getNoticeDetail(noticeDTO);
    }
    
    // 공지 수정 기능 구현 메서드
    public void updateNotice(NoticeDTO noticeDTO) {
      noticeMapper.updateNotice(noticeDTO);
    }

    // 공지 삭제
    public void delNotice(int noticeId){
      noticeMapper.delNotice(noticeId);
    }

    // 공지 이미지 조회
    public List<String> getNoticeImgList(int noticeId){
      return noticeMapper.getNoticeImgList(noticeId);
    }
}
