package org.example.core.repository;

import org.example.core.model.Movie;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends MovieAppRepository<Movie, Long> {
  @Query("select m.title, m.year from Movie m")
  List<Object[]> getAllMoviesWithoutActors();

  @Query("select distinct m from Movie m where m.id=:mid")
  @EntityGraph(value = "movieWithActors", type =
      EntityGraph.EntityGraphType.LOAD)
  Movie getMovieWithActors(@Param("mid") Long mid);
}
