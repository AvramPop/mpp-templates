package org.example.core.service;

import lombok.extern.slf4j.Slf4j;
import org.example.core.model.Movie;
import org.example.core.repository.ActorRepository;
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
  @Autowired
  private MovieRepository movieRepository;
  @Autowired
  private ActorRepository actorRepository;
  @Override
  public List<Movie> getAllMovies(){
    return movieRepository
        .getAllMoviesWithoutActors()
        .stream()
        .map(objects -> new Movie((String) objects[0], (int) objects[1], new ArrayList<>()))
        .collect(Collectors.toList());
  }

  @Override
  public Movie getMovieWithActors(Long movieId){
    return movieRepository.getMovieWithActors(movieId);
  }

  @Override
  @Transactional
  public Movie addActor(Long movieId, Long actorId){
     movieRepository.findById(movieId).ifPresent(movie -> movie.getActors().add(actorRepository.getOne(actorId)));
     return movieRepository.findById(movieId).orElse(null);
  }
}
