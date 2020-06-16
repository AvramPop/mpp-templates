package org.example.web;

import lombok.extern.slf4j.Slf4j;
import org.example.core.service.ActorService;
import org.example.core.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CodeDriver {
  @Autowired MovieService movieService;

  @Autowired ActorService actorService;

  void run() {
    movieService.addActor(1L, 2L);

    actorService.getAllActors().forEach(actor -> log.info(actor.toString()));
    actorService.getAllAvailableActors().forEach(actor -> log.info(actor.toString()));
    log.info(movieService.getMovieWithActors(1L).toString());
  }
}
