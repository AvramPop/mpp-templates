package org.example.core.repository;

import org.example.core.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface StudentRepository extends JpaRepository<Student, Integer> {
  List<Student> findAllByName(String name);
}
