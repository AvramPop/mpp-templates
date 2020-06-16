package org.example.core.model;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Actor extends BaseEntity<Long>{
  @Column(name = "name", unique = true)
  private String name;

  private Integer rating;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "mid")
  private Movie movie;
}
