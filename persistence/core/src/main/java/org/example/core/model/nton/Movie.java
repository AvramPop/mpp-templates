package org.example.core.model.nton;

import lombok.*;
import org.example.core.model.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movie")
@Builder
@Getter
@Setter
@NamedEntityGraphs({
    @NamedEntityGraph(name = "allMoviesWithActors",
        attributeNodes = @NamedAttributeNode(value = "movieCasts", subgraph = "castWithActors"),
        subgraphs = @NamedSubgraph(name = "castWithActors",
            attributeNodes = @NamedAttributeNode(value = "actor")
        ))
})
public class Movie extends BaseEntity<Integer> {
  private String name;

  @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<MovieCast> movieCasts;

  @Override
  public String toString(){
    return "Movie{" +
        "id=" + getId() + '\'' +
        ", name='" + name + '\'' +
        ", casts=" + movieCasts.size() +
        '}';
  }
}

