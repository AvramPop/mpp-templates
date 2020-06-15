package org.example.server.infrastructure;

import org.example.server.service.SensorService;

public class HandlerManager {
  private final TCPServer server;
  private final SensorService sensorService;
  boolean keepGoing = true;
  String toShutdown;
  //  private final StudentService studentService;

  public HandlerManager(TCPServer server, SensorService sensorService) {
    this.server = server;
    this.sensorService = sensorService;
  }

  private void addSensorHandler() {
    server.addHandler(
        MessageHeader.SENSOR_ADD,
        (request) -> {
          System.out.println("received" + request.getBody());
          String[] parsedRequest = request.getBody().split(",");
          try {
            sensorService.saveSensor(
                parsedRequest[0],
                Integer.parseInt(parsedRequest[1]),
                Integer.parseInt(parsedRequest[2]));
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          if (keepGoing) {
            return new Message(MessageHeader.OK_REQUEST, "");
          } else {
            if (parsedRequest[0].equals(toShutdown)) {
              System.out.println("dying");
              return new Message(MessageHeader.CLIENT_SHUTDOWN, toShutdown);
            }
            return new Message(MessageHeader.OK_REQUEST, "");
          }
        });
  }

  public void addHandlers() {
    addSensorHandler();
    serverShutdownHandler();
  }

  private void serverShutdownHandler() {
    System.out.println("SHUTDOWN!!!!");
    server.addHandler(
        MessageHeader.CLIENT_SHUTDOWN,
        (request) -> {
          System.out.println("received" + request.getBody());
          keepGoing = false;
          toShutdown = request.getBody();
          System.out.println(toShutdown);
          return new Message(MessageHeader.BAD_REQUEST, "");
        });

  }
}
