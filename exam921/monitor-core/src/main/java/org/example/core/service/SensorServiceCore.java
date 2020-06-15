package org.example.core.service;

import org.example.server.domain.Sensor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SensorServiceCore {
  List<Sensor> getAllSensors();

  List<String> getAllSensorNames();

  List<Sensor> findFirstFourForName(@Param("name") String nameParam);

  void stopSensor(String name);
}
