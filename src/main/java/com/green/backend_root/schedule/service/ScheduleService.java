package com.green.backend_root.schedule.service;

import com.green.backend_root.schedule.dto.ScheduleDTO;
import com.green.backend_root.schedule.mapper.ScheduleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
  private final ScheduleMapper scheduleMapper;

  // 일정 등록
  public void regSchedule(ScheduleDTO scheduleDTO) {
    scheduleMapper.regSchedule(scheduleDTO);
  }

  // 일정 목록 조회
  public List<ScheduleDTO> getScheduleList(String userId) {
    return scheduleMapper.getScheduleList(userId);
  }

  // 일정 상세 조회
  public ScheduleDTO getSchedule(int schedId) {
    return scheduleMapper.getSchedule(schedId);
  }

  // 일정 수정
  public void updateSchedule(ScheduleDTO scheduleDTO) {
    scheduleMapper.updateSchedule(scheduleDTO);
  }

  // 일정 삭제
  public void deleteSchedule(int schedId) {
    scheduleMapper.deleteSchedule(schedId);
  }

  // 완료 처리
  public void updateCompleted(ScheduleDTO scheduleDTO) {
    scheduleMapper.updateCompleted(scheduleDTO);
  }
}
