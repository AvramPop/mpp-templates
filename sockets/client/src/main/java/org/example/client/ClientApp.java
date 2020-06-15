package org.example.client;

import org.example.client.ui.Console;
import org.example.client.infrastructure.TCPClient;
import org.example.client.service.StudentClientService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientApp {
  public static void main(String[] args) {
    ExecutorService executorService =
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    TCPClient tcpClient = new TCPClient();
    StudentClientService studentClientService =
        new StudentClientService(executorService, tcpClient);
    Console console =
        new Console(
            studentClientService,
            executorService);
    console.run();
    executorService.shutdown();
  }
}
