package org.example.server.service;

import java.util.concurrent.ExecutorService;

public interface SensorService {
  void saveSensor(String name, int measurement, int id) throws InterruptedException;
  void setExecutorService(ExecutorService executorService);
}
