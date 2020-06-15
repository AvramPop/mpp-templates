package org.example.core.service.nton;

import lombok.extern.slf4j.Slf4j;
import org.example.core.model.nton.Actor;
import org.example.core.model.nton.MovieCast;
import org.example.core.model.nton.Movie;
import org.example.core.model.oneton.Author;
import org.example.core.repository.nton.ActorRepository;
import org.example.core.repository.nton.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService {
  @Autowired
  private MovieRepository movieRepository;

  @Autowired
  private ActorRepository actorRepository;

  @Override
  public List<Movie> findAll(){
    log.trace("findAll -- method entered");
    List<Movie> movies = movieRepository.findAllMoviesWithFullData();
    log.trace("findAll: result = {}", movies);
    return movies;
  }

  @Override
  @Transactional
  public boolean saveMovie(Integer id, String name){
    log.trace("saveMovie -- method entered. Params: id = {}, name = {}", id, name);
    if(movieRepository.existsById(id)){
      log.trace("saveMovie -- method failed. Movie with same id already in db.");
      return false;
    }
    Movie movie = new Movie(name, new ArrayList<>());
    movie.setId(id);
    movieRepository.save(movie);
    log.trace("saveMovie -- method finished successfully. Added movie {}", movie);
    return true;
  }

  @Override
  @Transactional
  public boolean deleteMovie(Integer id){
    log.trace("deleteMovie -- method entered. Params: id = {}", id);
    if(id == null || id < 0){
      log.trace("deleteMovie -- method failed. Invalid id.");
      throw new IllegalArgumentException("Invalid id!");
    }
    if(!movieRepository.existsById(id)){
      log.trace("deleteMovie -- method failed. Movie with id not in db.");
      return false;
    }
    movieRepository.deleteById(id);
    return true;
  }

  @Override
  public List<Actor> getAllActors(Integer id){
    return movieRepository.findAllMoviesWithFullData()
        .stream()
        .filter(movie -> movie.getId().equals(id))
        .findFirst()
        .get()
        .getMovieCasts()
        .stream()
        .map(MovieCast::getActor)
        .collect(Collectors.toList());
  }

  @Override
  public List<MovieCast> findAllCasts(){
    log.trace("findAllCasts -- method entered");

    return movieRepository.findAllMoviesWithFullData()
        .stream()
        .map(Movie::getMovieCasts)
        .distinct()
        .flatMap(Collection::stream)
        .collect(Collectors.toList());

  }

  @Override
  @Transactional
  public boolean saveCast(Integer id, Integer actorId, Integer movieId, int salary){
    MovieCast movieCast = MovieCast.builder()
        .actor(actorRepository.findAll().stream().filter(actor -> actor.getId() == actorId).findFirst().get())
        .movie(movieRepository.findAll().stream().filter(movie -> movie.getId() == movieId).findFirst().get())
        .salary(salary)
        .build();
    movieCast.setId(id);
    movieRepository.findAllMoviesWithFullData()
        .stream()
        .filter(movie -> movie.getId().equals(movieId))
        .findFirst()
        .get()
        .getMovieCasts()
        .add(movieCast);
    return true;
  }

  @Override
  @Transactional
  public boolean deleteCast(Integer id){
//    movieRepository.findAllMoviesWithFullData()
//        .stream()
//        .filter(movie -> movie.getId().equals(id))
//        .findFirst()
//        .get()
//        .getMovieCasts()
//        .removeIf(cast -> cast.getId().equals(id));
//    return true;
    Optional<Movie> parent = movieRepository.findAllMoviesWithFullData()
        .stream()
        .filter(movie ->
            movie.getMovieCasts().stream().anyMatch(cast -> cast.getId().equals(id))
        )
        .findFirst();
    parent.get().getMovieCasts().removeIf(cast -> cast.getId().equals(id));
    return true;
  }

  @Override
  @Transactional
  public boolean updateCast(Integer id, Integer actorId, Integer movieId, int salary){
    MovieCast movieCast = movieRepository.findAllMoviesWithFullData()
        .stream()
        .filter(movie -> movie.getId().equals(movieId))
        .findFirst()
        .get()
        .getMovieCasts()
        .stream()
        .filter(streamCast -> streamCast.getId().equals(id))
        .findFirst()
        .get();
    movieCast.setSalary(salary);
    movieCast.setActor(actorRepository.findAll().stream().filter(actor -> actor.getId() == actorId).findFirst().get());
    movieCast.setMovie(movieRepository.findAll().stream().filter(movie -> movie.getId() == movieId).findFirst().get());
    return true;
  }

}
