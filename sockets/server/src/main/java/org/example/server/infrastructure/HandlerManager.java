package org.example.server.infrastructure;

import org.example.common.domain.Student;
import org.example.common.exceptions.ValidatorException;
import org.example.common.infrastructure.Message;
import org.example.common.infrastructure.MessageHeader;
import org.example.common.infrastructure.StringEntityFactory;
import org.example.common.service.StudentService;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class HandlerManager {
  private final TCPServer server;
  private final StudentService studentService;

  public HandlerManager(TCPServer server, StudentService studentService) {
    this.server = server;
    this.studentService = studentService;
  }

  public void addHandlers() {
    allStudentsHandler();
    addStudentHandler();
    updateStudentHandler();
    deleteStudentHandler();

    serverShutdownHandler();
  }

  private void serverShutdownHandler() {
    server.addHandler(
        MessageHeader.SERVER_SHUTDOWN, (request) -> new Message(MessageHeader.OK_REQUEST, ""));
  }

  private void deleteStudentHandler() {
    server.addHandler(
        MessageHeader.STUDENT_DELETE,
        (request) -> {
          try {
            if (studentService.deleteStudent(Integer.parseInt(request.getBody())).get()) {
              return new Message(MessageHeader.OK_REQUEST, "");
            } else {
              return new Message(MessageHeader.BAD_REQUEST, "");
            }
          } catch (IllegalArgumentException ex) {
            return new Message(MessageHeader.BAD_REQUEST, "Invalid input\n" + ex.getMessage());
          } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
          }
          return null;
        });
  }
  /** Adds the handler for that functionality */
  private void updateStudentHandler() {
    server.addHandler(
        MessageHeader.STUDENT_UPDATE,
        (request) -> {
          String[] parsedRequest = request.getBody().split(",");

          try {
            if (studentService.updateStudent(
                Integer.parseInt(parsedRequest[0]), parsedRequest[1]).get()) {
              return new Message(MessageHeader.OK_REQUEST, "");
            } else {
              return new Message(MessageHeader.BAD_REQUEST, "");
            }
          } catch (ValidatorException ex) {
            return new Message(MessageHeader.BAD_REQUEST, "Invalid input\n" + ex.getMessage());
          } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
          }
          return null;
        });
  }
  /** Adds the handler for that functionality */
  private void addStudentHandler() {
    server.addHandler(
        MessageHeader.STUDENT_ADD,
        (request) -> {
          String[] parsedRequest = request.getBody().split(",");
          try {
            if (studentService
                .saveStudent(Integer.parseInt(parsedRequest[0]), parsedRequest[1])
                .get()) {
              return new Message(MessageHeader.OK_REQUEST, "");
            } else {
              return new Message(MessageHeader.BAD_REQUEST, "Entity already in storage");
            }
          } catch (ValidatorException ex) {
            return new Message(MessageHeader.BAD_REQUEST, "Invalid input\n" + ex.getMessage());
          } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
          }
          return null;
        });
  }
  /** Adds the handler for that functionality */
  private void allStudentsHandler() {
    server.addHandler(
        MessageHeader.STUDENT_ALL,
        (request) -> {
          Future<List<Student>> future = studentService.findAll();
          try {
            List<Student> result = future.get();
            return new Message(
                MessageHeader.OK_REQUEST, StringEntityFactory.collectionToMessageBody(result));
          } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return new Message(MessageHeader.BAD_REQUEST, e.getMessage());
          }
        });
  }
}
