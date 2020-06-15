package org.example.core.service;

import org.example.server.domain.Sensor;
import org.example.server.infrastructure.Message;
import org.example.server.infrastructure.MessageHeader;
import org.example.server.repository.SensorRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.Executors;

@Service
public class SensorServiceCoreImpl implements SensorServiceCore {
  @Autowired private SensorRepository sensorRepository;

  @Override
  public List<Sensor> getAllSensors() {
    return sensorRepository.findAll();
  }

  @Override
  public List<String> getAllSensorNames() {
    return sensorRepository.findAllNames();
  }

  @Override
  public List<Sensor> findFirstFourForName(String nameParam) {
    return sensorRepository.findFirstFourForName(nameParam);
  }

  @Override
  public void stopSensor(String name) {
    Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
        .submit(
            () -> {
              Message request = new Message(MessageHeader.CLIENT_SHUTDOWN, name);
              Message response = sendAndReceive(request);
            });
  }

  public Message sendAndReceive(Message request) {
    try (Socket socket = new Socket(Message.HOST, Message.PORT);
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream()) {
      // System.out.println("sendAndReceive - sending request: " + request);
      request.writeTo(os);

      // System.out.println("sendAndReceive - received response: ");
      Message response = new Message();
      response.readFrom(is);
      //  System.out.println(response);

      return response;
    } catch (IOException e) {
      throw new ServiceException("error connection to server " + e.getMessage(), e);
    }
  }
}
