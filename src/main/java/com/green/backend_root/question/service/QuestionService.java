package com.green.backend_root.question.service;

import com.green.backend_root.question.dto.QuestionDTO;
import com.green.backend_root.question.dto.QuestionImgDTO;
import com.green.backend_root.question.dto.SearchQuestionDTO;
import com.green.backend_root.question.mapper.QuestionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
  private final QuestionMapper questionMapper;

  // 문의 등록
  @Transactional(rollbackFor = Exception.class)
  public void regQst(List<QuestionImgDTO> questionImgDTOList, QuestionDTO questionDTO){
    // 이미지 등록 시 다음 문의 번호를 받을 변수
    int nextQstId = questionMapper.getQstId();

    // 문의 등록
    System.out.println("변경 전" + questionDTO);
    questionDTO.setQstId(nextQstId);
    System.out.println("조건" + questionDTO);
    if(questionDTO.getQstType().equals("일반문의")){
      questionDTO.setAlertSensors(null);
    }
    System.out.println("변경 후" + questionDTO);
    questionMapper.regQst(questionDTO);

    // 문의 이미지 등록
    if(!questionImgDTOList.isEmpty()){
      for(QuestionImgDTO img : questionImgDTOList){
        img.setQstId(nextQstId);
      }
      questionMapper.regQstImgs(questionImgDTOList);
    }
  }

  // 문의 상세 조회 (답변 페이지에도 이용)
  public QuestionDTO getQstDetail(QuestionDTO questionDTO){
    // 문의 상세 조회 시 이미지 등록 여부를 판단
    questionDTO.setImgCnt(questionMapper.getImgCnt(questionDTO));
    // 문의 상세 조회
    return questionMapper.getQstDetail(questionDTO);
  }

  // 문의 목록 조회
  public List<QuestionDTO> getQstList(SearchQuestionDTO searchQuestionDTO){
    return questionMapper.getQstList(searchQuestionDTO);
  }

  // 답변 진행 중 개수
  public int getQstStatusCnt(){
    return questionMapper.getQstStatusCnt();
  }

  // 문의 수정
  public void updateQst(QuestionDTO questionDTO){
    questionMapper.updateQst(questionDTO);
  }

  // 문의 삭제
  public void delQst(int qstId){
    questionMapper.delQst(qstId);
  }

  // 문의 이미지 조회
  public List<String> getQstImgList(int qstId){
    return questionMapper.getQstImgList(qstId);
  }
}
