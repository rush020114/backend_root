package com.green.backend_root.application.mapper;

import com.green.backend_root.application.dto.ApplicationDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApplicationMapper {
  // 서비스 신청
  void regService(ApplicationDTO applicationDTO);

  // 서비스 기본 정보 조회
  ApplicationDTO getServiceInfo(String userId);
}
