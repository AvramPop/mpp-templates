package org.example.core.service;

import org.example.core.model.Discipline;

import java.util.List;

public interface DisciplineService {
  List<Discipline> findAll();

  boolean updateDiscipline(Integer id, String newDescription);

  boolean saveDiscipline(Integer id, String description);

  boolean deleteDiscipline(Integer id);
}
