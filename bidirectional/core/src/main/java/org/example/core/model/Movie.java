package org.example.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NamedEntityGraphs({
    @NamedEntityGraph(name = "movieWithActors",
        attributeNodes = @NamedAttributeNode(value = "actors")),
})
public class Movie extends BaseEntity<Long>{

  @Column(name = "title", unique = true)
  private String title;

  private int year;

  @OneToMany(mappedBy = "movie", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Actor> actors;
}
