package org.example.core.service;

import org.example.core.model.Movie;

import java.util.List;

public interface MovieService {
  List<Movie> getMoviesByYear(int year, boolean lessThan);
  List<Movie> getMoviesWithActorsByYear(int year, boolean lessThan);
  void deleteActor(Long movieId, Long actorId);
}
