package com.green.backend_root.notice.service;

import com.green.backend_root.notice.dto.NoticeDTO;
import com.green.backend_root.notice.mapper.NoticeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeMapper noticeMapper;
    
    // 공지 등록 기능 구현 메서드
    public void regNotice(NoticeDTO noticeDTO) {
        // 공지 번호 조회 및 설정
        int noticeId = noticeMapper.getNoticeId();
        noticeDTO.setNoticeId(noticeId);
        
        noticeMapper.regNotice(noticeDTO);
    }
    
    // 공지 목록 조회 기능 구현 메서드
    public List<NoticeDTO> getNoticeList() {
        return noticeMapper.getNoticeList();
    }
    
    // 공지 상세 조회 기능 구현 메서드
    public NoticeDTO getNoticeDetail(NoticeDTO noticeDTO) {
        return noticeMapper.getNoticeDetail(noticeDTO);
    }
    
    // 공지 수정 기능 구현 메서드
    public void updateNotice(NoticeDTO noticeDTO) {
        noticeMapper.updateNotice(noticeDTO);
    }
}
