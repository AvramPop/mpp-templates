package org.example.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.example.common.domain.Student;
import org.example.common.domain.exceptions.ValidatorException;
import org.example.common.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class StudentServiceClient implements StudentService {
  @Autowired private StudentService studentService;

  @Override
  public boolean saveStudent(Integer id, String name)
      throws ValidatorException {
    return studentService.saveStudent(id, name);
  }

  @Override
  public List<Student> findAll() {
    return studentService.findAll();
  }

  @Override
  public boolean deleteStudent(Integer id) {
    return studentService.deleteStudent(id);
  }

  @Override
  public boolean updateStudent(Integer id, String name)
      throws ValidatorException {
    return studentService.updateStudent(id, name);
  }

}
