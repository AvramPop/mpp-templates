//package org.example.server.service;
//
//import lombok.extern.slf4j.Slf4j;
//import org.example.common.domain.Student;
//import org.example.common.service.StudentService;
//import org.example.server.repository.StudentRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Future;
//
//@Service
//@Slf4j
//public class StudentServerService implements StudentService {
//  private ExecutorService executorService;
//  @Autowired StudentRepository studentRepository;
//
//  public void setExecutorService(ExecutorService executorService){
//    this.executorService = executorService;
//  }
//
//  @Override
//  public Future<List<Student>> findAll() {
//    log.trace("findAll -- method entered");
//    List<Student> students = studentRepository.findAll();
//    log.trace("findAll: result = {}", students);
//    return executorService.submit(() -> students);
//  }
//
//  @Override
//  @Transactional
//  public Future<Boolean> updateStudent(Integer id, String newName) {
//    log.trace("updateStudent -- method entered. Params: id = {}, name = {}", id, newName);
//    Optional<Student> repositoryStudent = studentRepository.findById(id);
//    if (repositoryStudent.isPresent()) {
//      repositoryStudent.get().setName(newName);
//    } else {
//      log.trace("updateStudent -- method failed. Student with id does not exist in db.");
//      return executorService.submit(() -> false);
//    }
//    log.trace(
//        "updateStudent -- method finished successfully. Updated student {}",
//        repositoryStudent.get());
//    return executorService.submit(() -> true);
//  }
//
//  @Override
//  @Transactional
//  public Future<Boolean> saveStudent(Integer id, String name) {
//    log.trace("saveStudent -- method entered. Params: id = {}, name = {}", id, name);
//    if (studentRepository.existsById(id)) {
//      log.trace("saveStudent -- method failed. Student with same id already in db.");
//      return executorService.submit(() -> false);
//    }
//    Student student = new Student(id, name);
//    studentRepository.save(student);
//    log.trace("saveStudent -- method finished successfully. Added student {}", student);
//    return executorService.submit(() -> true);
//  }
//
//  @Override
//  @Transactional
//  public Future<Boolean> deleteStudent(Integer id) {
//    log.trace("deleteStudent -- method entered. Params: id = {}", id);
//    if (id == null || id < 0) {
//      log.trace("deleteStudent -- method failed. Invalid id.");
//      throw new IllegalArgumentException("Invalid id!");
//    }
//    if (!studentRepository.existsById(id)) {
//      log.trace("deleteStudent -- method failed. Student with id not in db.");
//      return executorService.submit(() -> false);
//    }
//    studentRepository.deleteById(id);
//    return executorService.submit(() -> true);
//  }
//}
