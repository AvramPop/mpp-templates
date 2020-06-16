package org.example.old.domain;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Student {
  private Integer id;
  private String name;
}
