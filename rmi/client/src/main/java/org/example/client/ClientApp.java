package org.example.client;

import org.example.client.ui.Console;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.example.common.service.StudentService;

/** Created by radu. */
public class ClientApp {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext("org.example.client.config");
    StudentService studentService = context.getBean(StudentService.class);

    Console console = new Console(studentService);
    console.run();

    System.out.println("bye");
  }
}
