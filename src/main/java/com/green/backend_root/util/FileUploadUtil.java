package com.green.backend_root.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Question과 Notice의 파일 업로드를 하나로 통합
// 멤버변수가 static인 이유 : 메서드가 static인데 상수를 메서드 안에서 사용하므로
public class FileUploadUtil {
  // 학원 경로
  private static final String BASE_PATH = "D:\\01-STUDY\\dev\\team\\backend_root\\src\\main\\resources\\static\\upload_files\\";

  // 집 경로
  // private static final String BASE_PATH = "C:\\dev\\team\\backend_root\\src\\main\\resources\\static\\upload_files\\";

  // 단일 파일 업로드
  // uploadPath : 업로드 경로 (QUESTION, NOTICE)
  public static String[] uploadFile(MultipartFile file, UploadPath uploadPath){
    // 최종 경로
    String fullPath = BASE_PATH + uploadPath.getPath();

    // 폴더가 없으면 생성
    File directory = new File(fullPath);
    if(!directory.exists()){
      directory.mkdirs(); // 폴더 생성
    }

    String attachedFileName = UUID.randomUUID().toString();

    String extension = file.getOriginalFilename().substring(
            file.getOriginalFilename().lastIndexOf('.')
    );

    attachedFileName += extension;

    File saveFile = new File(fullPath + File.separator + attachedFileName);

    try {
      file.transferTo(saveFile);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    // 원본 파일명, 첨부 파일명 반환
    return new String[] {file.getOriginalFilename(), attachedFileName};
  }

  // 여러 파일 업로드
  public static List<String[]> uploadFiles(MultipartFile[] files, UploadPath uploadPath){
    List<String[]> fileList = new ArrayList<>();

    for(MultipartFile file : files){
      fileList.add(uploadFile(file, uploadPath));
    }

    return fileList;
  }

  // 파일 삭제
  public static void deleteFiles(List<String> attachedFileNames, UploadPath uploadPath){
    String fullPath = BASE_PATH + File.separator + uploadPath.getPath();

    if(attachedFileNames != null && !(attachedFileNames.isEmpty())){
      for(String fileName : attachedFileNames) {
        File file = new File(fullPath + File.separator + fileName);
        if(file.exists()){
          file.delete();
        }
      }
    }
  }
}
