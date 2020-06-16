package org.example.web.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//public class MovieController {
//  public static final Logger logger = LoggerFactory.getLogger(StudentController.class);
//
//  @Autowired
//  private StudentService studentService;
//
//  @Autowired private StudentConverter studentConverter;
//
//  @RequestMapping(value = "/students/all", method = RequestMethod.GET)
//  public StudentsDto getStudents() {
//    logger.trace("getStudents -- method entered");
//    List<Student> students = studentService.findAll();
//    logger.trace("getStudents: students={}", students);
//    return new StudentsDto(studentConverter.convertModelsToDtos(students));
//  }
//
//  @RequestMapping(value = "/students/filtered/{name}", method = RequestMethod.GET)
//  public StudentsDto getStudentsFiltered(@PathVariable String name) {
//    logger.trace("getStudentsFiltered -- method entered. Data: {}", name);
//    List<Student> students = studentService.findStudentsByName(name);
//    logger.trace("getStudentsFiltered: students={}", students);
//    return new StudentsDto(studentConverter.convertModelsToDtos(students));
//  }
//
//  @RequestMapping(value = "/students/add", method = RequestMethod.POST)
//  public ResponseDto addStudent(@RequestBody StudentDto studentDto) {
//    logger.trace("addStudent -- method entered. Data: {}", studentDto.toString());
//    if(studentService.saveStudent(studentDto.getId(), studentDto.getName())) {
//      logger.trace("addStudent -- successful. Added student: {}", studentDto.toString());
//      return new ResponseDto("200");
//    } else {
//      logger.trace("addStudent -- failed");
//      return new ResponseDto("404");
//    }
//  }
//
//  @RequestMapping(value = "/students/up", method = RequestMethod.PUT)
//  public ResponseDto updateStudent(@RequestBody StudentDto studentDto) {
//    logger.trace("updateStudent -- method entered. Data: {}", studentDto.toString());
//    if(studentService.updateStudent(studentDto.getId(), studentDto.getName())) {
//      logger.trace("updateStudent -- successful. Updated student: {}", studentDto.toString());
//      return new ResponseDto("200");
//    } else {
//      logger.trace("updateStudent -- failed");
//      return new ResponseDto("404");
//    }
//  }
//
//  @RequestMapping(value = "/students/delete/{id}", method = RequestMethod.DELETE)
//  public ResponseDto deleteStudent(@PathVariable Integer id) {
//    logger.trace("deleteStudent -- method entered. Id: {}", id);
//    if(studentService.deleteStudent(id)) {
//      logger.trace("deleteStudent -- successful. Deleted student with id: {}", id);
//      return new ResponseDto("200");
//    } else {
//      logger.trace("deleteStudent -- failed");
//      return new ResponseDto("404");
//    }
//  }
//}