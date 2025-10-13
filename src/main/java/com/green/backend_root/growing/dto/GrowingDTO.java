package com.green.backend_root.growing.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GrowingDTO {
  private int temperNum;
  private float temper;
  private float humidity;
  private float soilHumidity;
  private int illumination;
  private LocalDateTime createDate;
}
