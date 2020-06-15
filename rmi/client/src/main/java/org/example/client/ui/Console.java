package org.example.client.ui;

import org.example.common.domain.Student;
import org.example.common.domain.exceptions.ClassReflectionException;
import org.example.common.domain.exceptions.RepositoryException;
import org.example.common.domain.exceptions.ValidatorException;
import org.example.common.service.StudentService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class Console {
  private StudentService studentService;
  private HashMap<String, Runnable> dictionaryOfCommands;

  public Console(
      StudentService studentService) {
    this.studentService = studentService;
    // I use lambda methods with a hash table to not to make if statements
    // if the thing fails it gets a null pointer exception
    // which means not a valid command
    initDictionaryOfCommands();
  }

  private void initDictionaryOfCommands() {
    dictionaryOfCommands = new HashMap<>();
    dictionaryOfCommands.put("add student", this::addStudent);
    dictionaryOfCommands.put("print students", this::printStudents);
    dictionaryOfCommands.put("update student", this::updateStudent);
    dictionaryOfCommands.put("delete student", this::deleteStudent);
    dictionaryOfCommands.put("exit", () -> System.exit(0));
  }

  /** ro.ubb.Main loop of the user interface */
  public void run() {
    while (true) {
      BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
      try {
        String inputString = input.readLine();
        dictionaryOfCommands.get(inputString).run();
      } catch (ValidatorException ex) {
        ex.printStackTrace();
        System.out.println(ex.getMessage());
      } catch (IOException ex) {
        System.out.println("Error with input!");
      } catch (NullPointerException ex) {
        System.out.println("Not a vaild comand");
      }
    }
  }

  /** ro.ubb.UI method for adding a student */
  private void addStudent() {
    System.out.println("Read student {id,serialNumber, name, group}");
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    try {
      System.out.println("Enter id: ");
      int id = Integer.parseInt(input.readLine().strip());
      System.out.println("Enter name: ");
      String name = input.readLine().strip();

      CompletableFuture.supplyAsync(
              () -> {
                try {
                  if (studentService.saveStudent(id, name))
                    return "Student added";
                  return "Student not added, already in database";
                } catch (ValidatorException ex) {
                  return ex.getMessage();
                }
              })
          .thenAcceptAsync(System.out::println);

    } catch (ValidatorException ex) {
      System.out.println(ex.getMessage());
    } catch (IOException | NumberFormatException e) {
      System.out.println("invalid input");
    }
  }
  /** ro.ubb.UI method for printing all students */
  private void printStudents() {

    CompletableFuture.supplyAsync(
            () -> {
              try {
                return studentService.findAll().stream()
                    .map(Student::toString)
                    .reduce("", (s1, s2) -> s1 + System.lineSeparator() + s2);
              } catch (NullPointerException e) {
                return "Getting students error";
              }
            })
        .thenAcceptAsync(System.out::println);
  }
  /** ro.ubb.UI method update a student */
  private void updateStudent() {
    System.out.println("Update student {id,serialNumber, name, group}");
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    try {
      System.out.println("Enter id: ");
      int id = Integer.parseInt(input.readLine().strip());
      System.out.println("Enter name: ");
      String name = input.readLine().strip();

      CompletableFuture.supplyAsync(
              () -> {
                try {
                  if (studentService.updateStudent(id, name))
                    return "Student updated";
                  return "Student not updated, student with the given id is not in the database";
                } catch (ValidatorException ex) {
                  return ex.getMessage();
                }
              })
          .thenAcceptAsync(System.out::println);
    } catch (ValidatorException ex) {
      // ex.printStackTrace();
      System.out.println(ex.getMessage());
    } catch (IOException | NumberFormatException e) {
      //      e.printStackTrace();
      System.err.println("invalid input");
    }
  }
  /** ro.ubb.UI method deletes a student */
  private void deleteStudent() {
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    int id;
    try {
      System.out.println("Enter id: ");
      id = Integer.parseInt(input.readLine().strip());

      CompletableFuture.supplyAsync(
              () -> {
                try {

                  if (studentService.deleteStudent(id)) {
                    return "Delete successful!";
                  } else {
                    return "Delete failed, entity with the given id is not in the database";
                  }
                } catch (IllegalArgumentException ex) {
                  return ex.getMessage();
                }
              })
          .thenAcceptAsync(System.out::println);

    } catch (IOException | NumberFormatException ex) {
      System.out.println("Invalid input!");
    }
  }
}
