package org.example.server.infrastructure;

import org.example.server.domain.SensorDto;

import java.util.*;
import java.util.stream.Collectors;

public class StringEntityFactory {
//
//  public static String collectionToMessageBody(
//      Collection<Student> collectionToConvert) {
//    return collectionToConvert.stream()
//        .map(StringEntityFactory::studentToFileLine)
//        .collect(Collectors.joining("\n"));
//  }

  public static String sensorToFileLine(SensorDto sensorDto) {
    String delimiter = ",";
    return
         sensorDto.getName() + delimiter + sensorDto.getMeasurement() + delimiter + sensorDto.getId();
  }
//
//  public static SensorDto sensorFromMessageLine(String messageLine) {
//    List<String> params = Arrays.asList(messageLine.split(","));
//    return new SensorDto(params.get(0), Integer.parseInt(params.get(1)));
//  }
//
//
//  public static <K, V> String pairToMessage(AbstractMap.SimpleEntry<K, V> pair) {
//    return pair.getKey().toString() + "," + pair.getValue().toString();
//  }
//
//  public static <T> String simpleValueToMessage(T value) {
//    return value.toString();
//  }
//
//  private static String collectionToLine(List<Student> list) {
//    return list.stream()
//        .map(student -> studentToFileLine(student) + ";")
//        .reduce("", String::concat)
//        .replaceFirst(".$", ""); // removes last semicolon
//  }

}