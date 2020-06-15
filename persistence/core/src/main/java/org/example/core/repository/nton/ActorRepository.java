package org.example.core.repository.nton;

import org.example.core.model.nton.Actor;
import org.example.core.model.nton.Movie;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
  @Query("select distinct a from Actor a")
  @EntityGraph(value = "allActorsWithMovies", type =
      EntityGraph.EntityGraphType.LOAD)
  List<Actor> findAllActorsWithFullData();
}
