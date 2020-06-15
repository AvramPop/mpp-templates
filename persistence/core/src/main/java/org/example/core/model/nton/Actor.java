package org.example.core.model.nton;

import lombok.*;
import org.example.core.model.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="actor")
@Builder
@Getter
@Setter
@NamedEntityGraphs({
    @NamedEntityGraph(name = "allActorsWithMovies",
        attributeNodes = @NamedAttributeNode(value = "movieCasts", subgraph = "castWithMovies"),
        subgraphs = @NamedSubgraph(name = "castWithMovies",
            attributeNodes = @NamedAttributeNode(value = "movie")
        ))
})
public class Actor extends BaseEntity<Integer> {
  private String name;

  @OneToMany(mappedBy = "actor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<MovieCast> movieCasts;

  @Override
  public String toString(){
    return "Actor{" +
        "id=" + getId() + '\'' +
        ", name='" + name + '\'' +
        ", casts=" + movieCasts.size() +
        '}';
  }
}
