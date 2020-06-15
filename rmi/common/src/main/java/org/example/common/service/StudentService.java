package org.example.common.service;

import org.example.common.domain.Student;
import org.example.common.domain.exceptions.ValidatorException;

import java.util.List;
import java.util.Set;

public interface StudentService {
  List<Student> findAll();
  boolean updateStudent(Integer id, String newName);
  boolean saveStudent(Integer id, String name);
  boolean deleteStudent(Integer id);
}