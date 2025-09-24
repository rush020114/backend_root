package com.green.backend_root.answer.controller;

import com.green.backend_root.answer.dto.AnswerDTO;
import com.green.backend_root.answer.service.AnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/answers")
public class AnswerController {
  private final AnswerService answerService;

  // 답변 등록 api
  @PostMapping("")
  public void regAns(@RequestBody AnswerDTO answerDTO){
    log.info(answerDTO.toString());
    answerService.regAns(answerDTO);
  }
}
