package org.example.common.infrastructure;

import org.example.common.domain.Student;

import java.util.*;
import java.util.stream.Collectors;

public class StringEntityFactory {

  public static String collectionToMessageBody(
      Collection<Student> collectionToConvert) {
    return collectionToConvert.stream()
        .map(StringEntityFactory::studentToFileLine)
        .collect(Collectors.joining("\n"));
  }

  public static String studentToFileLine(Student student) {
    String delimiter = ",";
    return student.getId()
        + delimiter
        + student.getName();
  }

  public static Student studentFromMessageLine(String messageLine) {
    List<String> params = Arrays.asList(messageLine.split(","));
    return new Student(Integer.parseInt(params.get(0)), params.get(1));
  }


  public static <K, V> String pairToMessage(AbstractMap.SimpleEntry<K, V> pair) {
    return pair.getKey().toString() + "," + pair.getValue().toString();
  }

  public static <T> String simpleValueToMessage(T value) {
    return value.toString();
  }

  private static String collectionToLine(List<Student> list) {
    return list.stream()
        .map(student -> studentToFileLine(student) + ";")
        .reduce("", String::concat)
        .replaceFirst(".$", ""); // removes last semicolon
  }

}