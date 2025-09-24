package com.green.backend_root.question.controller;

import com.green.backend_root.question.dto.QuestionDTO;
import com.green.backend_root.question.dto.QuestionImgDTO;
import com.green.backend_root.question.service.QuestionService;
import com.green.backend_root.util.QuestionFileUploadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionController {
  private final QuestionService questionService;

  // 문의 등록 api
  @PostMapping("")
  public void regQst(@RequestParam(name = "questionImgs", required = false) MultipartFile[] questionImgs
                    , QuestionDTO questionDTO){

    List<QuestionImgDTO> questionImgDTOList = new ArrayList<>();

    // 이미지들 업로드
    if(questionImgs != null && questionImgs.length > 0){
      questionImgDTOList = QuestionFileUploadUtil.multipleQstFileUpload(questionImgs);
    }
    // 문의 등록
    questionService.regQst(questionImgDTOList, questionDTO);
  }

  // 문의 상세 조회 api
  @GetMapping("/detail")
  public QuestionDTO getQstDetail(QuestionDTO questionDTO){
    return questionService.getQstDetail(questionDTO);
  }

  // 문의 목록 조회 api
  @GetMapping("")
  public List<QuestionDTO> getQstList(){
    return questionService.getQstList();
  }
}
