package com.green.backend_root.util;


// 파일 업로드 경로를 관리하는 Enum
// Question과 Notice의 업로드 경로를 통합 관리
public enum UploadPath {
  // Question 이미지 업로드 경로
  QUESTION("question"),

  // Notice 이미지 업로드 경로
  NOTICE("notice"),

  // User 이미지 업로드 경로
  USER("user");

  // 실제 폴더명을 저장하는 변수
  private final String path;

  // 생성자: 경로 값을 받아서 저장
  // @param path 업로드 폴더명 (예: "question", "notice")
  UploadPath(String path) {
    this.path = path;
  }

  // 저장된 경로를 반환하는 메서드
  // @return 업로드 폴더명
  // 예: UploadPath.QUESTION.getPath() → "question"
  public String getPath() {
    return this.path;
  }
}