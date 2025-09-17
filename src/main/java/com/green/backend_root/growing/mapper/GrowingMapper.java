package com.green.backend_root.growing.mapper;

import com.green.backend_root.growing.dto.GrowingDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GrowingMapper {

  // 센서 데이터 상세 조회
  public List<GrowingDTO> getGrowingList();

}
