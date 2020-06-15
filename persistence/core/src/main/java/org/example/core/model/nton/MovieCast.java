package org.example.core.model.nton;

import lombok.*;
import org.example.core.model.BaseEntity;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name="moviecast")
@Setter
public class MovieCast extends BaseEntity<Integer> {
  int salary;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "actorId")
  private Actor actor;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "movieId")
  private Movie movie;
}
