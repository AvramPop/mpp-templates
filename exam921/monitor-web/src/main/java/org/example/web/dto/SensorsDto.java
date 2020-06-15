package org.example.web.dto;

import lombok.*;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Builder
public class SensorsDto {
  private List<SensorWebDto> sensors;
}
