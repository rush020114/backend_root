package com.green.backend_root.answer.controller;

import com.green.backend_root.answer.dto.AnswerDTO;
import com.green.backend_root.answer.service.AnswerService;
import com.green.backend_root.common.NotificationController;
import com.green.backend_root.question.dto.QuestionDTO;
import com.green.backend_root.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/answers")
public class AnswerController {
  private final AnswerService answerService;
  private final QuestionService questionService;
  private final NotificationController notificationController;

  // 답변 등록 api
  @PostMapping("")
  public ResponseEntity<?> regAns(@RequestBody AnswerDTO answerDTO, QuestionDTO questionDTO){
    try {
      // 이용자 아이디를 받아오기 위한 dto
      questionDTO.setQstId(answerDTO.getQstId());

      // 답변 등록
      answerService.regAns(answerDTO);

      // 이용자에에 알림
      notificationController.notifyUser(questionService.getQstDetail(questionDTO).getUserId(), "답변이 등록되었습니다.");
      return ResponseEntity
              .status(HttpStatus.CREATED)
              .body("답변 완료");
    } catch (Exception e) {
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("등록 중 서버 오류 발생");
    }
  }

  // 답변 조회 api
  @GetMapping("/{qstId}")
  public ResponseEntity<?> getAns(@PathVariable("qstId") int qstId){
     try {
       return ResponseEntity
               .status(HttpStatus.OK)
               .body(answerService.getAns(qstId));
     } catch (Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("조회 중 서버 오류 발생");
     }
  }

  // 답변 수정 api
  @PutMapping("")
  public ResponseEntity<?> updateAns(@RequestBody AnswerDTO answerDTO){
    try {
      answerService.updateAns(answerDTO);
      return ResponseEntity
              .status(HttpStatus.OK)
              .body("수정 완료");
    } catch (Exception e) {
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("수정 중 서버 오류 발생");
    }
  }
}
