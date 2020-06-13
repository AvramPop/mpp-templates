package org.example.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Builder
public class DisciplineDto {
  private Integer id;
  private String description;
}
