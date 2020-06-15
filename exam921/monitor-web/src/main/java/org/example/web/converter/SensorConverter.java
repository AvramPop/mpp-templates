package org.example.web.converter;

import org.example.server.domain.Sensor;
import org.example.web.dto.SensorWebDto;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class SensorConverter {
  public Sensor convertDtoToModel(SensorWebDto dto) {
    return Sensor.builder()
        .name(dto.getName())
        .id(dto.getId())
        .measurement(dto.getMeasurement())
        .time(dto.getTime())
        .build();
  }

  public SensorWebDto convertModelToDto(Sensor sensor) {
    return SensorWebDto.builder()
        .id(sensor.getId())
        .name(sensor.getName())
        .time(sensor.getTime())
        .measurement(sensor.getMeasurement())
        .build();
  }

  public List<SensorWebDto> convertModelsToDtos(Collection<Sensor> students) {
    return students.stream().map(this::convertModelToDto).collect(Collectors.toList());
  }

  public List<String> convertNamesToDto(List<String> sensors){
    return sensors;

  }
}
