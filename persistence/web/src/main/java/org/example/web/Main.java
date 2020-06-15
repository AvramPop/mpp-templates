package org.example.web;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
  public static void main(String[] args){
    System.out.println("TESTING");
    AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(
            "org.example.web.config");

    CodeDriver codeDriver = context.getBean(CodeDriver.class);
    codeDriver.run();
    System.out.println("finished testing");
  }
}
