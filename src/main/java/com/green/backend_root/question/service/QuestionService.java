package com.green.backend_root.question.service;

import com.green.backend_root.question.dto.QuestionDTO;
import com.green.backend_root.question.dto.QuestionImgDTO;
import com.green.backend_root.question.mapper.QuestionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
  private final QuestionMapper questionMapper;

  // 문의 등록
  public void regQst(List<QuestionImgDTO> questionImgDTOList, QuestionDTO questionDTO){
    // 이미지 등록 시 다음 문의 번호를 받을 변수
    String nextQstId = questionMapper.getQstId();

    // 문의 등록
    questionMapper.regQst(questionDTO);

    // 문의 이미지 등록
    if(!questionImgDTOList.isEmpty()){
      for(QuestionImgDTO img : questionImgDTOList){
        img.setQstId(nextQstId);
      }
      questionMapper.regQstImgs(questionImgDTOList);
    }
  }
}
