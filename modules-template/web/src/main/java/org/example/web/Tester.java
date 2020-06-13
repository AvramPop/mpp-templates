package org.example.web;

import org.example.core.service.DisciplineService;
import org.example.core.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Tester {
  public static final Logger log = LoggerFactory.getLogger(Tester.class);
  @Autowired
  private DisciplineService disciplineService;
  @Autowired
  private StudentService studentService;

  public void run(){
    studentService.updateStudent(8, "AAAAA");
   // disciplineService.saveDiscipline(2, "update");
   // disciplineService.deleteDiscipline(-9);
//    disciplineService.findAll().forEach(discipline -> System.out.println(discipline.toString()));
  }
}
