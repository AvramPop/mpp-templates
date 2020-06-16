package org.example.web;

import lombok.extern.slf4j.Slf4j;
import org.example.core.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CodeDriver {
  @Autowired
  private StudentService studentService;
  public void run() {
    System.out.println(studentService.findAllNamesAndIds().size());
    System.out.println(studentService.findAllNamesAndIds().get(0).length);
//    studentService.findAllNamesAndIds().forEach(objects -> log.info(objects[0].toString() + " " + objects[1].toString()));
  }
}
