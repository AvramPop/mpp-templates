package org.example.core.service;

import lombok.extern.slf4j.Slf4j;
import org.example.core.model.Actor;
import org.example.core.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ActorServiceImpl implements ActorService {
  @Autowired
  private ActorRepository actorRepository;

  @Override
  public List<Actor> getAllActors(){
    return actorRepository.findAll();
  }

  @Override
  public List<Actor> getAllAvailableActors(){
    return actorRepository.findAll().stream().filter(actor -> actor.getMid() == null).collect(Collectors.toList());
  }
}
