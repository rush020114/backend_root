package com.green.backend_root.growing.service;

import com.green.backend_root.growing.dto.GrowingDTO;
import com.green.backend_root.growing.mapper.GrowingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GrowingService {
  private final GrowingMapper growingMapper;

  // 센서 데이터 상세 조회
  public List<GrowingDTO> getGrowingList(){
    return growingMapper.getGrowingList();
  }

  // 7일 간 센서 데이터 조회
  public List<GrowingDTO> getWeeklyGrowingList(){
    return growingMapper.getWeeklyGrowingList();
  }
}
