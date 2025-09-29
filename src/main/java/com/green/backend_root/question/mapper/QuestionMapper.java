package com.green.backend_root.question.mapper;

import com.green.backend_root.answer.dto.AnswerDTO;
import com.green.backend_root.question.dto.QuestionDTO;
import com.green.backend_root.question.dto.QuestionImgDTO;
import com.green.backend_root.question.dto.SearchQuestionDTO;
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

  // 문의 상세 조회 시 이미지 등록 여부를 판단
  int getImgCnt(QuestionDTO questionDTO);

  // 문의 상세 조회 (답변 페이지에도 이용)
  QuestionDTO getQstDetail(QuestionDTO questionDTO);

  // 문의 목록 조회
  List<QuestionDTO>  getQstList(SearchQuestionDTO searchQuestionDTO);

  // 답변 등록 시 답변 상태 바꾸기
  void updateQstStatus(AnswerDTO answerDTO);

  // 답변 진행 중 개수
  int getQstStatusCnt();

  // 문의 수정
  void updateQst(QuestionDTO questionDTO);

  // 문의 삭제
  void delQst(int qstId);

  // 문의 이미지 조회
  List<QuestionImgDTO> getQstImgList(int qstId);
}
