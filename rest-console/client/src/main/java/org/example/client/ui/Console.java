package org.example.client.ui;

import org.example.web.dto.ResponseDto;
import org.example.web.dto.StudentDto;
import org.example.web.dto.StudentsDto;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

@Component
public class Console {
  private HashMap<String, Runnable> dictionaryOfCommands;
  private static final String baseURL = "http://localhost:8080/api";
  private final RestTemplate restTemplate;

  public Console() {
    initDictionaryOfCommands();
    AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext("org.example.client.config");
    restTemplate = context.getBean(RestTemplate.class);
  }

  private void initDictionaryOfCommands() {
    dictionaryOfCommands = new HashMap<>();
    dictionaryOfCommands.put("add student", this::addStudent);
    dictionaryOfCommands.put("print students", this::printStudents);
    dictionaryOfCommands.put("update student", this::updateStudent);
    dictionaryOfCommands.put("delete student", this::deleteStudent);
    dictionaryOfCommands.put("filter students", this::filterStudentsByName);
    dictionaryOfCommands.put("exit", () -> System.exit(0));
  }

  public void run() {
    while (true) {
      BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
      try {
        String inputString = input.readLine();
        dictionaryOfCommands.get(inputString).run();
      } catch (IOException ex) {
        System.out.println("Error with input!");
      } catch (NullPointerException ex) {
        System.out.println("Not a vaild comand");
      }
    }
  }

  private void filterStudentsByName() {
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    String name;
    try {
      System.out.println("Enter name: ");
      name = input.readLine().strip();

    } catch (IOException ex) {
      System.out.println("Invalid input!");
      return;
    }
//    CompletableFuture.supplyAsync(
//            () ->
//                restTemplate.getForObject(
//                    baseURL + "/students/filtered/{name}", StudentsDto.class, name))
//        .thenAcceptAsync(result -> result.getStudents().forEach(System.out::println));
    CompletableFuture.supplyAsync(
        () -> {
          ResponseEntity<StudentsDto> response =
              restTemplate.exchange(baseURL + "/students/filtered/{name}", HttpMethod.GET, null, StudentsDto.class, name);
          return "Filtered students: " + response.getBody().getStudents()
              .stream()
              .map(StudentDto::toString)
              .reduce("", (a, b) -> a + "\n" + b);
        })
        .thenAcceptAsync(System.out::println);
  }

  private void deleteStudent() {
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    long id;
    try {
      System.out.println("Enter id: ");
      id = Long.parseLong(input.readLine().strip());
      CompletableFuture.supplyAsync(
          () -> {
            ResponseEntity<ResponseDto> response =
                restTemplate.exchange(baseURL + "/students/delete/{id}", HttpMethod.DELETE, null, ResponseDto.class, id);
            return "The message is: " + response.getBody().getMessage();
          })
          .thenAcceptAsync(System.out::println);

    } catch (IOException | NumberFormatException ex) {
      System.out.println("Invalid input!");
    }
  }

  private void updateStudent() {
    System.out.println("Update student {id, serialNumber, name, group}");
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    try {
      System.out.println("Enter id: ");
      int id = Integer.parseInt(input.readLine().strip());
      System.out.println("Enter name: ");
      String name = input.readLine().strip();
      CompletableFuture.supplyAsync(
          () -> {
            HttpEntity<StudentDto> studentDtoHttpEntity = new HttpEntity<>(new StudentDto(id, name));
            ResponseEntity<ResponseDto> response =
                restTemplate.exchange(baseURL + "/students/up", HttpMethod.PUT, studentDtoHttpEntity, ResponseDto.class);
            return "The message is: " + response.getBody().getMessage();
          })
          .thenAcceptAsync(System.out::println);
    } catch (IOException | NumberFormatException e) {
      System.out.println("invalid input");
    }
  }

  private void printStudents() {
    // alternative syntax to receive the good old json
//    ResponseEntity<String> response
//        = restTemplate.getForEntity(fooResourceUrl + "/1", String.class);
//    ObjectMapper mapper = new ObjectMapper();
//    JsonNode root = mapper.readTree(response.getBody());
//    JsonNode name = root.path("name");
    //--------
    CompletableFuture.supplyAsync(
        () -> {
          ResponseEntity<StudentsDto> response =
              restTemplate.exchange(baseURL + "/students/all", HttpMethod.GET, null, StudentsDto.class);
          return "All students: " + response.getBody().getStudents()
              .stream()
              .map(StudentDto::toString)
              .reduce("", (a, b) -> a + "\n" + b);
        })
        .thenAcceptAsync(System.out::println);
  }

  private void addStudent() {
    System.out.println("Read student {id, name}");
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    try {
      System.out.println("Enter id: ");
      int id = Integer.parseInt(input.readLine().strip());
      System.out.println("Enter name: ");
      String name = input.readLine().strip();
      CompletableFuture.supplyAsync(
              () -> {
                HttpEntity<StudentDto> studentDtoHttpEntity = new HttpEntity<>(new StudentDto(id, name));
                  ResponseEntity<ResponseDto> response =
                      restTemplate.exchange(baseURL + "/students/add", HttpMethod.POST, studentDtoHttpEntity, ResponseDto.class);
                  return "The message is: " + response.getBody().getMessage();
              })
          .thenAcceptAsync(System.out::println);
    } catch (IOException | NumberFormatException e) {
      System.out.println("invalid input");
    }
  }
}
