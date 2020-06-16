package org.example.core.service;

import lombok.extern.slf4j.Slf4j;
import org.example.core.model.Movie;
import org.example.core.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService {
  @Autowired private MovieRepository movieRepository;

  @Override
  public List<Movie> getMoviesByYear(int year, boolean lessThan) {
    return movieRepository.getAllMoviesWithoutActors().stream()
        .map(objects -> new Movie((String) objects[0], (int) objects[1], new ArrayList<>()))
        .filter(
            movie -> {
              if (lessThan) return movie.getYear() < year;
              else return movie.getYear() >= year;
            })
        .collect(Collectors.toList());
  }

  @Override
  public List<Movie> getMoviesWithActorsByYear(int year, boolean lessThan) {
    return movieRepository.getMoviesWithActors().stream()
        .filter(
            movie -> {
              if (lessThan) return movie.getYear() < year;
              else return movie.getYear() >= year;
            })
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void deleteActor(Long movieId, Long actorId) {
    movieRepository
        .findById(movieId)
        .ifPresent(
            movie -> {
              movie.getActors().removeIf(actor -> actor.getId().equals(actorId));
            });
  }
}
