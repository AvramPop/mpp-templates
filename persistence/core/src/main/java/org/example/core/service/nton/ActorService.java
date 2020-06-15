package org.example.core.service.nton;

import org.example.core.model.nton.Actor;
import org.example.core.model.nton.Movie;

import java.util.List;

public interface ActorService {
  List<Actor> findAll();
  boolean saveActor(Integer id, String name);
  boolean deleteActor(Integer id);
  List<Movie> getAllMovies(Integer id);
}
