package org.example.server.service;

import org.example.common.domain.Student;
import org.example.common.domain.exceptions.ValidatorException;
import org.example.common.service.StudentService;
import org.example.server.repository.StudentRepository;
import org.example.server.service.validators.StudentValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/** Created by radu. */
@Service
public class StudentServiceImpl implements StudentService {
  public static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

  @Autowired
  private StudentRepository studentRepository;
  @Autowired private StudentValidator studentValidator;

  @Override
  public List<Student> findAll() {
    logger.trace("findAll -- method entered");
    List<Student> students = studentRepository.findAll();
    logger.trace("findAll: result = {}", students);
    return students;
  }

  @Override
  @Transactional
  public boolean updateStudent(Integer id, String newName) {
    logger.trace("updateStudent -- method entered. Params: id = {}, name = {}", id, newName);
    try {
      studentValidator.validate(new Student(id, newName));
      Optional<Student> repositoryStudent = studentRepository.findById(id);
      if (repositoryStudent.isPresent()) {
        repositoryStudent.get().setName(newName);
      } else {
        logger.trace("updateStudent -- method failed. Student with id does not exist in db.");
        return false;
      }
      logger.trace(
          "updateStudent -- method finished successfully. Updated student {}",
          repositoryStudent.get());
      return true;
    } catch (ValidatorException exception) {
      logger.trace(
          "updateStudent -- method failed due to to validating exception. Message = {}",
          exception.getMessage());
      return false;
    }
  }

  @Override
  public boolean saveStudent(Integer id, String name) {
    logger.trace("saveStudent -- method entered. Params: id = {}, name = {}", id, name);
    if (studentRepository.existsById(id)) {
      logger.trace("saveStudent -- method failed. Student with same id already in db.");
      return false;
    }
    Student student = new Student(id, name);
    try {
      studentValidator.validate(student);
      studentRepository.save(student);
      logger.trace("saveStudent -- method finished successfully. Added student {}", student);
      return true;
    } catch (ValidatorException exception) {
      logger.trace(
          "saveStudent -- method failed due to to validating exception. Message = {}",
          exception.getMessage());
      return false;
    }
  }

  @Override
  public boolean deleteStudent(Integer id) {
    logger.trace("deleteStudent -- method entered. Params: id = {}", id);
    if (id == null || id < 0) {
      logger.trace("deleteStudent -- method failed. Invalid id.");
      throw new IllegalArgumentException("Invalid id!");
    }
    if (!studentRepository.existsById(id)) {
      logger.trace("deleteStudent -- method failed. Student with id not in db.");
      return false;
    }
    studentRepository.deleteById(id);
    return true;
  }
}