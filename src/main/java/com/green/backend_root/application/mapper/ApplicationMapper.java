package com.green.backend_root.application.mapper;

import com.green.backend_root.application.dto.ApplicationDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApplicationMapper {
  // 서비스 신청
  void regService(ApplicationDTO applicationDTO);

  // 서비스 기본 정보 조회
  ApplicationDTO getServiceInfo(String userId);
  
  // 서비스 신청 현황 조회
  List<ApplicationDTO> getApplicationList();

  // 서비스 승인 처리
  void approveApplication(int applNum);

  //이름으로 검색 조회
  List<ApplicationDTO> searchByName(String userName);

  // 총 이용일
  int getTotalDays(String userId);

  // 농장 정보 수정
  void updateFarmInfo(ApplicationDTO applicationDTO);
}
