package org.example.web;


import lombok.extern.slf4j.Slf4j;
import org.example.core.service.nton.ActorService;
import org.example.core.service.nton.MovieService;
import org.example.core.service.oneton.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CodeDriver {
  @Autowired
  private ActorService actorService;

  @Autowired
  private MovieService movieService;

  private void print(){
    actorService.findAll().forEach(actor -> log.trace(actor.toString()));
    movieService.findAll().forEach(movie -> log.trace(movie.toString()));
    movieService.findAllCasts().forEach(cast -> log.trace(cast.toString()));
  }

  public void run(){
//    actorService.saveActor(1, "actor1");
//    actorService.saveActor(2, "actor2");
//    movieService.saveMovie(1, "movie1");
//    movieService.saveMovie(2, "movie2");
//    movieService.saveCast(3, 2, 1, 100);
//    movieService.saveCast(4, 2, 2, 100);
    movieService.deleteMovie(1);
    movieService.findAllCasts().forEach(cast -> log.trace(String.valueOf(cast.getSalary())));


    print();

  }
}
