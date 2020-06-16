package org.example.core.service;

import org.example.core.model.Movie;

import java.util.List;

public interface MovieService {
  List<Movie> getAllMovies();
  Movie getMovieWithActors(Long movieId);
  Movie addActor(Long movieId, Long actorId);
}
