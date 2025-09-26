package com.green.backend_root.util;

import com.green.backend_root.question.dto.QuestionImgDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class QuestionFileUploadUtil {
  public static QuestionImgDTO qstFileUpload(MultipartFile img){
    String uploadPath = "D:\\01-STUDY\\dev\\team\\backend_root\\src\\main\\resources\\static\\question_upload\\";

    String attachedImgName = UUID.randomUUID().toString();

    String extension = img.getOriginalFilename().substring(img.getOriginalFilename().lastIndexOf('.'));

    attachedImgName += extension;

    File file = new File(uploadPath + attachedImgName);

    try {
      img.transferTo(file);
    }catch (Exception e){
      System.out.println(e);
    }

    QuestionImgDTO questionImgDTO = new QuestionImgDTO();
    questionImgDTO.setOriginImgName(img.getOriginalFilename());
    questionImgDTO.setAttachedImgName(attachedImgName);
    return questionImgDTO;
  }

  public static List<QuestionImgDTO> multipleQstFileUpload(MultipartFile[] imgs){
    List<QuestionImgDTO> questionImgDTOList = new ArrayList<>();
    for(MultipartFile img : imgs){
      questionImgDTOList.add(qstFileUpload(img));
    }
    return questionImgDTOList;
  }

  // 이미지 삭제 메서드
  public static void QuestionFileDelete(List<QuestionImgDTO> imgs){

    String uploadPath = "D:\\01-STUDY\\dev\\team\\backend_root\\src\\main\\resources\\static\\question_upload\\";
    if(imgs != null &&  !imgs.isEmpty()){ // imgs 리스트가 비어있다면 실행 안 함
      for(QuestionImgDTO img : imgs){
        File file = new File(uploadPath + img.getAttachedImgName());
        if(file.exists()){ // 이 경로에 파일이 존재한다면
          file.delete(); // 삭제
        }
      }
    }
  }
}
