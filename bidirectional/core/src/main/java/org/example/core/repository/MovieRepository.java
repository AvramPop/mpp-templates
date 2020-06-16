package org.example.core.repository;

import org.example.core.model.Movie;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends MovieAppRepository<Movie, Long> {
  @Query("select distinct m from Movie m")
  @EntityGraph(value = "movieWithActors", type =
      EntityGraph.EntityGraphType.LOAD)
  List<Movie> getMoviesWithActors();

  @Query("select m.title, m.year from Movie m")
  List<Object[]> getAllMoviesWithoutActors();
}
