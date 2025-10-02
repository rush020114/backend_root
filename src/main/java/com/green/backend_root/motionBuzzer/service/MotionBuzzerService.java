package com.green.backend_root.motionBuzzer.service;

import com.green.backend_root.motionBuzzer.dto.MotionBuzzerDTO;
import com.green.backend_root.motionBuzzer.mapper.MotionBuzzerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MotionBuzzerService {
  private final MotionBuzzerMapper motionBuzzerMapper;

  // 로그 목록 조회
  public List<MotionBuzzerDTO> getMotionBuzzerList(){
    return motionBuzzerMapper.getMotionBuzzerList();
  }

  // 통계 조회
  public MotionBuzzerDTO getMotionBuzzerStats() {
    return motionBuzzerMapper.getMotionBuzzerStats();
  };

}
