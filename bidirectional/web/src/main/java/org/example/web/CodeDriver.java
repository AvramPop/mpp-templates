package org.example.web;

import lombok.extern.slf4j.Slf4j;
import org.example.core.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CodeDriver {
  @Autowired MovieService movieService;

  void run() {
    movieService.deleteActor(1L, 1L);
    movieService.getMoviesWithActorsByYear(2000, false).get(0).getActors().forEach(actor -> log.info(actor.getName()));

  }
}
