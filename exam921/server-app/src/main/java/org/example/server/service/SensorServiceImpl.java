package org.example.server.service;

import lombok.extern.slf4j.Slf4j;
import org.example.server.domain.Sensor;
import org.example.server.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.ExecutorService;

@Service
@Slf4j
public class SensorServiceImpl implements SensorService{
  @Autowired
  SensorRepository sensorRepository;
    private ExecutorService executorService;


  public void saveSensor(String name, int measurement, int id) throws InterruptedException{
    java.util.Date date = new java.util.Date();
    java.sql.Timestamp sqlTimeStamp = new java.sql.Timestamp(date.getTime());
    Sensor sensor = Sensor.builder().name(name).measurement(measurement).time(Time.valueOf(LocalTime.now())).build();
    System.out.println(name);
    System.out.println(measurement);
    System.out.println(id);
    Thread.sleep(new Random().nextInt(10000));
    sensorRepository.save(sensor);
    executorService.submit(() -> true);
  }

  public void setExecutorService(ExecutorService executorService){
    this.executorService = executorService;
  }
}
