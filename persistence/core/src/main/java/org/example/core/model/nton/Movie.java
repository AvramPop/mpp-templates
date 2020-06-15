package org.example.core.model.nton;

import lombok.*;
import org.example.core.model.BaseEntity;

import javax.persistence.Entity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Movie extends BaseEntity<Integer> {
  private String name;
}

