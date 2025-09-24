package com.green.backend_root.answer.service;

import com.green.backend_root.answer.dto.AnswerDTO;
import com.green.backend_root.answer.mapper.AnswerMapper;
import com.green.backend_root.question.mapper.QuestionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnswerService {
  private final AnswerMapper answerMapper;
  private final QuestionMapper questionMapper;

  // 답변 등록
  @Transactional(rollbackFor = Exception.class)
  public void regAns(AnswerDTO answerDTO){
    // 답변 등록
    answerMapper.regAns(answerDTO);

    // 답변 등록 시 답변 상태 바꾸기
    questionMapper.updateQstStatus(answerDTO);
  }
}
