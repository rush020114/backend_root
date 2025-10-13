package com.green.backend_root.answer.mapper;

import com.green.backend_root.answer.dto.AnswerDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AnswerMapper {
  // 답변 등록
  void regAns(AnswerDTO answerDTO);

  // 답변 조회
  AnswerDTO getAns(int qstId);

  // 답변 수정
  void updateAns(AnswerDTO answerDTO);
}
