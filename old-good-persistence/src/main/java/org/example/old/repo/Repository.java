package org.example.old.repo;

import org.example.old.domain.Student;

import java.util.List;
import java.util.Optional;

public interface Repository {
  Optional<Student> findOne(Integer id);

  List<Student> findAll();

  boolean save(Student entity);

  boolean delete(Integer id);

  boolean update(Student entity);
}
