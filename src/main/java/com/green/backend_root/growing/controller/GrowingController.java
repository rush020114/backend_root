package com.green.backend_root.growing.controller;

import com.green.backend_root.growing.dto.GrowingDTO;
import com.green.backend_root.growing.service.GrowingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/growings")
@RequiredArgsConstructor
public class GrowingController {
  private final GrowingService growingService;

  // 센서 데이터 상세 조회
  @GetMapping("")
  public List<GrowingDTO> getGrowingList() {
    return growingService.getGrowingList();
  }

}
