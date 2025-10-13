package com.green.backend_root.notice.controller;

import com.green.backend_root.notice.dto.NoticeDTO;
import com.green.backend_root.notice.dto.NoticeImgDTO;
import com.green.backend_root.notice.dto.SearchNoticeDTO;
import com.green.backend_root.notice.service.NoticeService;
import com.green.backend_root.util.FileUploadUtil;
import com.green.backend_root.util.UploadPath;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notices")
public class NoticeController {
  private final NoticeService noticeService;

  // 공지 등록 API
  @PostMapping("")
  public ResponseEntity<?> regNotice(@RequestParam(name = "noticeImgs", required = false) MultipartFile[] noticeImgs
          , NoticeDTO noticeDTO){
    try {
      List<NoticeImgDTO> noticeImgDTOList = new ArrayList<>();

      // 이미지들 업로드
      if(noticeImgs != null && noticeImgs.length > 0){
        List<String[]> uploadedImgs = FileUploadUtil.uploadFiles(noticeImgs, UploadPath.NOTICE);

        for(String[] fileInfo : uploadedImgs){
          NoticeImgDTO noticeImgDTO = new NoticeImgDTO();
          noticeImgDTO.setOriginImgName(fileInfo[0]);
          noticeImgDTO.setAttachedImgName(fileInfo[1]);
          noticeImgDTOList.add(noticeImgDTO);
        }
      }
      // 문의 등록
      noticeService.regNotice(noticeImgDTOList, noticeDTO);
      return ResponseEntity
              .status(HttpStatus.CREATED)
              .body("등록 성공");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("등록 중 서버 오류 발생");
    }
  }

  // 공지 목록 조회 API
  @GetMapping("")
  public ResponseEntity<?> getNoticeList(SearchNoticeDTO searchNoticeDTO) {
    try {
      List<NoticeDTO> noticeList = noticeService.getNoticeList(searchNoticeDTO);
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(noticeList);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("목록 조회 중 오류가 발생했습니다.");
    }
  }

  // 공지 상세 조회 API
  @GetMapping("/detail")
  public ResponseEntity<?> getNoticeDetail(NoticeDTO noticeDTO) {
    try {
      NoticeDTO notice = noticeService.getNoticeDetail(noticeDTO);
      if (notice == null) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("해당 공지사항을 찾을 수 없습니다.");
      }
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(notice);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("상세 조회 중 오류가 발생했습니다.");
    }
  }
  
  // 공지 수정 API
  @PutMapping("/{noticeId}")
  public ResponseEntity<?> updateNotice(
          @PathVariable("noticeId") int noticeId,
          @RequestBody NoticeDTO noticeDTO) {
    System.out.println(noticeDTO.toString());
    try {
      noticeDTO.setNoticeId(noticeId);
      noticeService.updateNotice(noticeDTO);
      return ResponseEntity
              .status(HttpStatus.OK)
              .body("수정 성공");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("수정 중 오류가 발생했습니다.");
    }
  }

  // 공지 삭제 api
  @DeleteMapping("/{noticeId}")
  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<?> delNotice(@PathVariable("noticeId") int noticeId){
    try {
      // 공지 삭제 시 이미지 삭제
      List<String> noticeImgDTOList = noticeService.getNoticeImgList(noticeId);

      // 공지 삭제
      noticeService.delNotice(noticeId);

      // 이미지 삭제
      FileUploadUtil.deleteFiles(noticeImgDTOList, UploadPath.NOTICE);
      return ResponseEntity
              .status(HttpStatus.OK)
              .body("삭제 완료");
    } catch (Exception e) {
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("삭제 중 서버 오류 발생");
    }
  }

  // 공지 목록 삭제 api
  @PostMapping("/delete")
  public ResponseEntity<?> delNoticeList(@RequestBody NoticeDTO noticeDTO){
    try {
      int deleteCnt = noticeService.delNoticeList(noticeDTO.getNoticeIdArr());
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(deleteCnt + "건의 공지가 성공적으로 삭제되었습니다.");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("공지 목록 삭제 중 서버 오류 발생");
    }
  }
}
