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
    studentService.findAll().forEach(student -> log.info(student.getName()));
    log.info("----------------");
    studentService.findByNameLikeCustom("rest").forEach(student -> log.info(student.getName()));
  }
}
