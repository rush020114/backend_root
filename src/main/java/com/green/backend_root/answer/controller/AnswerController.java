package com.green.backend_root.answer.controller;

import com.green.backend_root.answer.dto.AnswerDTO;
import com.green.backend_root.answer.service.AnswerService;
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

  // 답변 등록 api
  @PostMapping("")
  public ResponseEntity<?> regAns(@RequestBody AnswerDTO answerDTO){
    try {
      answerService.regAns(answerDTO);
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
