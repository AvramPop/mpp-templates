package org.example.old.model;

import org.example.old.domain.Student;

public interface Repository {
  void save(Student student);
  void findAll();
}
