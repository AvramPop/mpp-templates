package org.example.core.repository.nton;

import org.example.core.model.nton.Actor;
import org.example.core.model.nton.Movie;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
  @Query("select distinct m from Movie m")
  @EntityGraph(value = "allMoviesWithActors", type =
      EntityGraph.EntityGraphType.LOAD)
  List<Movie> findAllMoviesWithFullData();
}
