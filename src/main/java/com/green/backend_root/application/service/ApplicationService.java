package com.green.backend_root.application.service;

import com.green.backend_root.application.dto.ApplicationDTO;
import com.green.backend_root.application.mapper.ApplicationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {
  public final ApplicationMapper applicationMapper;

  // 서비스 신청
  public void regService (ApplicationDTO applicationDTO){
    applicationMapper.regService(applicationDTO);
  }

  // 서비스 기본 정보 조회
  public ApplicationDTO getServiceInfo(String userId){
    return applicationMapper.getServiceInfo(userId);
  }
  
  // 서비스 신청 현황 조회
  public List<ApplicationDTO> getApplicationList() {
    return applicationMapper.getApplicationList();
  }

  // 서비스 승인 처리
  public void approveApplication(int applNum) {
    applicationMapper.approveApplication(applNum);
  }

  // 회원명으로 검색
  public List<ApplicationDTO> searchByName(String userName) {
    return applicationMapper.searchByName(userName);
  }

  // 총 이용일
  public int getTotalDays(String userId){
    return applicationMapper.getTotalDays(userId);
  }

  // 농장 정보 수정
  public void updateFarmInfo(ApplicationDTO applicationDTO){
    applicationMapper.updateFarmInfo(applicationDTO);
  }
}
