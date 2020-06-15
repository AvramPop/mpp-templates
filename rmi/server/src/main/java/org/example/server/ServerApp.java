package org.example.server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/** Created by radu. */
public class ServerApp {
  public static void main(String[] args) {
    System.out.println("server starting");

    AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext("org.example.server.config");
  }
}
