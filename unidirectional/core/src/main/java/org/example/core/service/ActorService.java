package org.example.core.service;

import org.example.core.model.Actor;

import java.util.List;

public interface ActorService {
  List<Actor> getAllActors();
  List<Actor> getAllAvailableActors();
}
