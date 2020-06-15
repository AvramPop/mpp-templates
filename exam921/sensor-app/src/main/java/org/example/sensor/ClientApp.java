package org.example.sensor;

import org.example.sensor.service.SensorClientService;
import org.example.sensor.ui.Console;
import org.example.sensor.infrastructure.TCPClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientApp {
  public static void main(String[] args) {
    ExecutorService executorService =
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    TCPClient tcpClient = new TCPClient();
    SensorClientService sensorClientService =
        new SensorClientService(executorService, tcpClient);
    Console console =
        new Console(
            sensorClientService,
            executorService);
    console.run();
    executorService.shutdown();
  }
}
