package com.green.backend_root.application.service;

import com.green.backend_root.application.dto.ApplicationDTO;
import com.green.backend_root.application.mapper.ApplicationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
