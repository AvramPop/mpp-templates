package org.example.server;

import org.example.common.service.StudentService;
import org.example.server.infrastructure.HandlerManager;
import org.example.server.infrastructure.TCPServer;
import org.example.server.service.StudentServerService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerApp {

  public static void main(String[] args) {

    System.out.println("server started");

    AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext("org.example.server.config");
    ExecutorService executorService =
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    StudentService studentService =
        context.getBean(StudentService.class);
    studentService.setExecutorService(executorService);
    try {
      TCPServer tcpServer = new TCPServer(executorService);
      HandlerManager handlerManager =
          new HandlerManager(tcpServer, studentService);
      handlerManager.addHandlers();
      tcpServer.startServer();
      executorService.shutdown();
    } catch (RuntimeException ex) {
      ex.printStackTrace();
    }
  }
}
