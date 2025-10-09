package com.green.backend_root.schedule.mapper;

import com.green.backend_root.schedule.dto.ScheduleDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScheduleMapper {
  // 일정 등록
  void regSchedule(ScheduleDTO scheduleDTO);

  // 일정 목록 조회 (사용자별)
  List<ScheduleDTO> getScheduleList(String userId);

  // 일정 상세 조회
  ScheduleDTO getSchedule(int schedId);

  // 일정 수정
  void updateSchedule(ScheduleDTO scheduleDTO);

  // 일정 삭제
  void deleteSchedule(int schedId);

  // 완료 처리
  void updateCompleted(ScheduleDTO scheduleDTO);
}
