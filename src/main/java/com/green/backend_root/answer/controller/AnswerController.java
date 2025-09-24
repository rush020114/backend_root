package com.green.backend_root.answer.controller;

import com.green.backend_root.answer.dto.AnswerDTO;
import com.green.backend_root.answer.service.AnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

  // 답변 조회 api
  @GetMapping("/{qstId}")
  public AnswerDTO getAns(@PathVariable("qstId") int qstId){
    return answerService.getAns(qstId);
  }
}
