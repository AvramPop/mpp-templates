package org.example.core.service;

import org.example.core.model.Student;

import java.util.List;

public interface StudentService {
  List<Student> findAll();
  boolean updateStudent(Integer id, String newName);
  boolean saveStudent(Integer id, String name);
  boolean deleteStudent(Long id);
}
