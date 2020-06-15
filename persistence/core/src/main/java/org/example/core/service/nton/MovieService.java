package org.example.core.service.nton;


import org.example.core.model.nton.Actor;
import org.example.core.model.nton.MovieCast;
import org.example.core.model.nton.Movie;

import java.util.List;

public interface MovieService {
  List<Movie> findAll();
  boolean saveMovie(Integer id, String name);
  boolean deleteMovie(Integer id);
  List<Actor> getAllActors(Integer id);

  List<MovieCast> findAllCasts();
  boolean saveCast(Integer id, Integer actorId, Integer movieId, int salary);
  boolean deleteCast(Integer id);
  boolean updateCast(Integer id, Integer actorId, Integer movieId, int salary);
}
