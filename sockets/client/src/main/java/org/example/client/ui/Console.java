package org.example.client.ui;

import org.example.client.service.StudentClientService;
import org.example.common.domain.Student;
import org.example.common.exceptions.ServiceException;
import org.example.common.exceptions.ValidatorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/** Console based user interface for the client. */
public class Console {
  private StudentClientService studentService;
  private HashMap<String, Runnable> dictionaryOfCommands;
  private ExecutorService executorService;

  public Console(StudentClientService studentService, ExecutorService executorService) {
    this.studentService = studentService;
    this.executorService = executorService;
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
    dictionaryOfCommands.put("shutdown server", this::shutDownServer);
    dictionaryOfCommands.put("exit", () -> System.exit(0));
  }

  /** Take specific user input and print server's answer to the call getLabProblemById call. */
  private void shutDownServer() {
    studentService.shutDownServer();
    System.exit(0);
  }

  public void run() {
    while (true) {
      BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
      try {
        String inputString = input.readLine();
        dictionaryOfCommands.get(inputString).run();
      } catch (ValidatorException ex) {
        ex.printStackTrace();
        System.out.println(ex.getMessage());
      } catch (ConnectException | ServiceException ex) {
        System.out.println(ex.getMessage());
      } catch (IOException ex) {
        System.out.println("Error with input!");
      } catch (NullPointerException ex) {
        System.out.println("Not a vaild comand");
      }
    }
  }

  /** Take specific user input and print server's answer to the call addStudent call. */
  private void addStudent() {
    System.out.println("Read student {id, name}");
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    try {
      System.out.println("Enter id: ");
      int id = Integer.parseInt(input.readLine().strip());
      System.out.println("Enter name: ");
      String name = input.readLine().strip();
      Future<Boolean> studentFuture = studentService.saveStudent(id, name);

      CompletableFuture.supplyAsync(
              () -> {
                try {
                  studentFuture.get();
                  return "Student added";
                } catch (InterruptedException | ExecutionException e) {
                  return e.getMessage().substring(e.getMessage().indexOf(" ") + 1)
                      + "\n"
                      + "Student not added";
                }
              })
          .thenAcceptAsync(System.out::println);

    } catch (ValidatorException ex) {
      System.out.println(ex.getMessage());
    } catch (IOException | NumberFormatException e) {
      System.out.println("invalid input");
    }
  }
  /** Take specific user input and print server's answer to the call printStudents call. */
  private void printStudents() {
    Future<List<Student>> students = studentService.findAll();

    CompletableFuture.supplyAsync(
            () -> {
              try {
                return students.get().stream()
                    .map(Student::toString)
                    .reduce("", (s1, s2) -> s1 + System.lineSeparator() + s2);
              } catch (InterruptedException | ExecutionException e) {
                return e.getMessage().substring(e.getMessage().indexOf(" ") + 1);
              }
            })
        .thenAcceptAsync(System.out::println);
  }

  private void updateStudent() {
    System.out.println("Update student {id, name}");
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    try {
      System.out.println("Enter id: ");
      int id = Integer.parseInt(input.readLine().strip());
      System.out.println("Enter name: ");
      String name = input.readLine().strip();

      Future<Boolean> labProblemFuture =
          studentService.updateStudent(id, name);
      CompletableFuture.supplyAsync(
              () -> {
                try {
                  labProblemFuture.get();
                  return "Student updated";
                } catch (InterruptedException | ExecutionException e) {
                  return e.getMessage().substring(e.getMessage().indexOf(" ") + 1)
                      + "\nStudent not updated";
                }
              })
          .thenAcceptAsync(System.out::println);
    } catch (ValidatorException ex) {
      System.out.println(ex.getMessage());
    } catch (IOException | NumberFormatException e) {
      System.err.println("invalid input");
    }
  }

  private void deleteStudent() {
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    int id;
    try {
      System.out.println("Enter id: ");
      id = Integer.parseInt(input.readLine().strip());

      Future<Boolean> studentFuture = studentService.deleteStudent(id);
      CompletableFuture.supplyAsync(
              () -> {
                try {
                  studentFuture.get();
                  return "Delete successful";
                } catch (InterruptedException | ExecutionException e) {
                  return e.getMessage().substring(e.getMessage().indexOf(" ") + 1)
                      + "\nDelete failed!";
                }
              })
          .thenAcceptAsync(System.out::println);

    } catch (IOException | NumberFormatException ex) {
      System.out.println("Invalid input!");
    }
  }
}
