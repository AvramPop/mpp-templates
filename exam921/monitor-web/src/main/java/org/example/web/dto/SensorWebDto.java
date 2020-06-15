package org.example.web.dto;

import lombok.*;

import java.sql.Time;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Builder
public class SensorWebDto {
  private Integer id;

  private String name;

  private int measurement;

  private Time time;
}
