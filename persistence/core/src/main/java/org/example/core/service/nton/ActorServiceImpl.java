package org.example.core.service.nton;

import lombok.extern.slf4j.Slf4j;
import org.example.core.model.nton.Actor;
import org.example.core.model.nton.MovieCast;
import org.example.core.model.nton.Movie;
import org.example.core.repository.nton.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ActorServiceImpl implements ActorService {
  @Autowired
  private ActorRepository actorRepository;

  @Override
  public List<Actor> findAll(){
    log.trace("findAll -- method entered");
    List<Actor> actors = actorRepository.findAllActorsWithFullData();
    log.trace("findAll: result = {}", actors);
    return actors;
  }

  @Override
  @Transactional
  public boolean saveActor(Integer id, String name){
    log.trace("saveActor -- method entered. Params: id = {}, name = {}", id, name);
    if(actorRepository.existsById(id)){
      log.trace("saveActor -- method failed. Actor with same id already in db.");
      return false;
    }
    Actor actor = new Actor(name, new ArrayList<>());
    actor.setId(id);
    actorRepository.save(actor);
    log.trace("saveActor -- method finished successfully. Added actor {}", actor);
    return true;
  }

  @Override
  @Transactional
  public boolean deleteActor(Integer id){
    log.trace("deleteActor -- method entered. Params: id = {}", id);
    if(id == null || id < 0){
      log.trace("deleteActor -- method failed. Invalid id.");
      throw new IllegalArgumentException("Invalid id!");
    }
    if(!actorRepository.existsById(id)){
      log.trace("deleteActor -- method failed. Actor with id not in db.");
      return false;
    }
    actorRepository.deleteById(id);
    return true;
  }

  @Override
  public List<Movie> getAllMovies(Integer id){
    return actorRepository.findAllActorsWithFullData()
        .stream()
        .filter(actor -> actor.getId().equals(id))
        .findFirst()
        .get()
        .getMovieCasts()
        .stream()
        .map(MovieCast::getMovie)
        .collect(Collectors.toList());
  }
}
