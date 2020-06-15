package org.example.web.controller;


import org.example.core.service.SensorServiceCore;
import org.example.server.domain.Sensor;
import org.example.web.converter.SensorConverter;
import org.example.web.dto.NamesDto;
import org.example.web.dto.SensorsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SensorController {

  @Autowired private SensorServiceCore sensorService;

  @Autowired private SensorConverter sensorConverter;

  @RequestMapping(value = "/all", method = RequestMethod.GET)
  public SensorsDto getSensors() {
    List<Sensor> sensors = sensorService.getAllSensors();
    return new SensorsDto(sensorConverter.convertModelsToDtos(sensors));
  }

  @RequestMapping(value = "/names", method = RequestMethod.GET)
  public NamesDto getSensorNames() {
    List<String> sensors = sensorService.getAllSensorNames();
    return new NamesDto(sensorConverter.convertNamesToDto(sensors));
  }

  @RequestMapping(value = "/sensors/{name}", method = RequestMethod.GET)
  public SensorsDto getSensorNames(@PathVariable String name) {
    List<Sensor> sensors = sensorService.findFirstFourForName(name);
    return new SensorsDto(sensorConverter.convertModelsToDtos(sensors));
  }

  @RequestMapping(value = "/stop/{name}", method = RequestMethod.POST)
  public SensorsDto stopSensor(@PathVariable String name) {
    System.out.println("controller" + name);
    sensorService.stopSensor(name);
    return new SensorsDto();
  }
}