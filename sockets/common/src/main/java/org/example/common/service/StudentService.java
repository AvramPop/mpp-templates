package org.example.common.service;

import org.example.common.domain.Student;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public interface StudentService {
  Future<List<Student>> findAll();
  Future<Boolean> updateStudent(Integer id, String newName);
  Future<Boolean> saveStudent(Integer id, String name);
  Future<Boolean> deleteStudent(Integer id);
  void setExecutorService(ExecutorService executorService);

  }
