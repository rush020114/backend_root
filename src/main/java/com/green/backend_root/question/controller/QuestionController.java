package com.green.backend_root.question.controller;

import com.green.backend_root.question.dto.QuestionDTO;
import com.green.backend_root.question.dto.QuestionImgDTO;
import com.green.backend_root.question.dto.SearchQuestionDTO;
import com.green.backend_root.question.service.QuestionService;
import com.green.backend_root.util.QuestionFileUploadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
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
@RequestMapping("/questions")
public class QuestionController {
  private final QuestionService questionService;

  // 문의 등록 api
  @PostMapping("")
  public ResponseEntity<?> regQst(@RequestParam(name = "questionImgs", required = false) MultipartFile[] questionImgs
                    , QuestionDTO questionDTO){
    try {
      if(questionDTO.getQstTitle().trim().isEmpty()){
        throw new Exception();
      }
      List<QuestionImgDTO> questionImgDTOList = new ArrayList<>();

      // 이미지들 업로드
      if(questionImgs != null && questionImgs.length > 0){
        questionImgDTOList = QuestionFileUploadUtil.multipleQstFileUpload(questionImgs);
      }
      // 문의 등록
      questionService.regQst(questionImgDTOList, questionDTO);
      return ResponseEntity
              .status(HttpStatus.CREATED)
              .body("등록완료");
    } catch (Exception e) {
      e.printStackTrace();
      boolean isParamError = questionDTO.getQstTitle() == null || questionDTO.getQstTitle().isEmpty();

      return ResponseEntity
              .status(
                isParamError ?
                HttpStatus.BAD_REQUEST :
                HttpStatus.INTERNAL_SERVER_ERROR
              )
              .body(
                isParamError ?
                "입력한 데이터가 올바른지 확인해주세요." :
                "문의 등록 중 서버 오류 발생"
              );
    }

  }

  // 문의 상세 조회 api
  @GetMapping("/detail")
  public ResponseEntity<?> getQstDetail(QuestionDTO questionDTO){
    try{
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(questionService.getQstDetail(questionDTO));

    }catch (Exception e){
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("조회 중 서버 오류 발생");
    }
  }

  // 문의 목록 조회 api
  @GetMapping("")
  public ResponseEntity<?> getQstList(SearchQuestionDTO searchQuestionDTO){
    try {
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(questionService.getQstList(searchQuestionDTO));
    } catch (Exception e){
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("조회 중 서버 오류 발생");
    }
  }

  // 답변 진행중 개수 조회 api
  @GetMapping("/status-cnt")
  public ResponseEntity<?> getQstStatusCnt(){
    try {
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(questionService.getQstStatusCnt());
    } catch (Exception e){
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("조회 중 서버 오류 발생");
    }
  }

  // 문의 수정 api
  @PutMapping("/{qstId}")
  public ResponseEntity<?> updateQst(@RequestBody QuestionDTO questionDTO, @PathVariable("qstId") int qstId){
    try {
      questionDTO.setQstId(qstId);
      questionService.updateQst(questionDTO);
      return ResponseEntity
              .status(HttpStatus.OK)
              .body("수정 완료");
    } catch (Exception e) {
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("수정 중 서버 오류 발생");
    }
  }

  // 문의 삭제 api
  @DeleteMapping("/{qstId}")
  @Transactional(rollbackFor = Exception.class)
  public ResponseEntity<?> delQst(@PathVariable("qstId") int qstId){
    try {
      // 문의 삭제 시 이미지도 삭제
      List<QuestionImgDTO> questionImgDTOList = questionService.getQstImgList(qstId);

      // 문의 삭제
      questionService.delQst(qstId);

      // 이미지 삭제
      QuestionFileUploadUtil.QuestionFileDelete(questionImgDTOList);
      return ResponseEntity
              .status(HttpStatus.OK)
              .body("삭제 완료");
    } catch (Exception e) {
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("삭제 중 서버 오류 발생");
    }
  }
}
