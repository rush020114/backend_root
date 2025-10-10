package com.green.backend_root.motionBuzzer.mapper;

import com.green.backend_root.motionBuzzer.dto.MotionBuzzerDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MotionBuzzerMapper {

  // 로그 목록 조회
  public List<MotionBuzzerDTO> getMotionBuzzerList();

  // 감지 횟수 + 최근 감지 시간 조회
  MotionBuzzerDTO getMotionBuzzerStats();

  // 오늘 제어 횟수 조회
  List<MotionBuzzerDTO> getTodayDeviceStatus();
}
