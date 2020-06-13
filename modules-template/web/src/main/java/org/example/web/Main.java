package org.example.web;

import org.example.core.service.DisciplineService;
import org.example.core.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

public class Main {

  public static void main(String[] args) {
    System.out.println("TESTING");
    AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(
            "org.example.web.config");

    Tester tester = context.getBean(Tester.class);
    tester.run();
    System.out.println("finished testing");
  }
}
