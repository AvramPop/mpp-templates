package org.example.sensor.service;

import org.example.sensor.infrastructure.TCPClient;
import org.example.server.domain.Sensor;
import org.example.server.domain.SensorDto;
import org.example.server.infrastructure.Message;
import org.example.server.infrastructure.MessageHeader;
import org.example.server.infrastructure.StringEntityFactory;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

public class SensorClientService {
  private final ExecutorService executorService;
  private final TCPClient tcpClient;
  public SensorClientService(ExecutorService executorService, TCPClient tcpClient){
    this.executorService = executorService;
    this.tcpClient = tcpClient;
  }

  public void sendData(String name, int lowerBound, int upperBound, int id) throws InterruptedException{
    boolean stop = false;
    var wrapper = new Object(){ boolean stop = false; };
    while (!wrapper.stop) {
      int randomNum = ThreadLocalRandom.current().nextInt(lowerBound, upperBound + 1);
      SensorDto sensor = SensorDto.builder().name(name).id(id).measurement(randomNum).build();
      executorService.submit(
          () -> {
            Message request =
                new Message(MessageHeader.SENSOR_ADD, StringEntityFactory.sensorToFileLine(sensor));
            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(MessageHeader.CLIENT_SHUTDOWN)
                && response.getBody().equals(name)) {
              System.out.println(response.getHeader());
              System.out.println(response.getBody());
              wrapper.stop = true;
            }
          });
      Thread.sleep(new Random().nextInt(10000));
    }
  }
}
