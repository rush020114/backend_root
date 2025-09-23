package com.green.backend_root.question.mapper;

import com.green.backend_root.question.dto.QuestionDTO;
import com.green.backend_root.question.dto.QuestionImgDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuestionMapper {
  // 문의 등록
  void regQst(QuestionDTO questionDTO);

  // 문의 이미지 등록을 위한 문의 번호 조회
  String getQstId();

  // 문의 이미지 등록
  void regQstImgs(List<QuestionImgDTO> questionImgDTOList);
}
