//package org.example.sensor.service;
//
//import org.example.sensor.infrastructure.TCPClient;
//import org.example.common.domain.Student;
//import org.example.common.exceptions.BadRequestException;
//import org.example.common.exceptions.ValidatorException;
//import org.example.common.infrastructure.Message;
//import org.example.common.infrastructure.MessageHeader;
//import org.example.common.infrastructure.StringEntityFactory;
//import org.example.common.service.StudentService;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Future;
//import java.util.stream.Collectors;
///** Client service to handle Student specific data communicated via socket. */
//public class StudentClientService implements StudentService {
//  private final ExecutorService executorService;
//  private final TCPClient tcpClient;
//
//  public StudentClientService(ExecutorService executorService, TCPClient tcpClient) {
//    this.executorService = executorService;
//    this.tcpClient = tcpClient;
//  }
//  public void shutDownServer() {
//    tcpClient.sendAndReceive(new Message(MessageHeader.SERVER_SHUTDOWN, ""));
//  }
//
//  @Override
//  public Future<Boolean> saveStudent(Integer id, String name)
//      throws ValidatorException {
//    Student newStudent = new Student(id, name);
//
//    return executorService.submit(
//        () -> {
//          Message request = new Message(MessageHeader.STUDENT_ADD, StringEntityFactory.studentToFileLine(newStudent));
//          Message response = tcpClient.sendAndReceive(request);
//          if (response.getHeader().equals(MessageHeader.BAD_REQUEST))
//            throw new BadRequestException("Addition failed, entity already in repository");
//
//          return null;
//        });
//  }
//
//  @Override
//  public Future<List<Student>> findAll() {
//    return executorService.submit(
//        () -> {
//          Message request = new Message(MessageHeader.STUDENT_ALL, "");
//          Message response = tcpClient.sendAndReceive(request);
//          if (response.getHeader().equals(MessageHeader.BAD_REQUEST))
//            throw new BadRequestException(response.getBody());
//
//          String[] lines = response.getBody().split(System.lineSeparator());
//          return Arrays.stream(lines)
//              .map(StringEntityFactory::studentFromMessageLine)
//              .collect(Collectors.toList());
//        });
//  }
//
//  @Override
//  public Future<Boolean> updateStudent(Integer id, String name)
//      throws ValidatorException {
//    Student newStudent = new Student(id, name);
//
//    return executorService.submit(
//        () -> {
//          Message request =
//              new Message(MessageHeader.STUDENT_UPDATE, StringEntityFactory.studentToFileLine(newStudent));
//          Message response = tcpClient.sendAndReceive(request);
//          if (response.getHeader().equals(MessageHeader.BAD_REQUEST))
//            throw new BadRequestException(response.getBody());
//          return true;
//        });
//  }
//  @Override
//  public Future<Boolean> deleteStudent(Integer id) {
//
//    return executorService.submit(
//        () -> {
//          Message request = new Message(MessageHeader.STUDENT_DELETE, id.toString());
//          Message response = tcpClient.sendAndReceive(request);
//          if (response.getHeader().equals(MessageHeader.BAD_REQUEST))
//            throw new BadRequestException(response.getBody());
//          return true;
//        });
//  }
//
//  @Override
//  public void setExecutorService(ExecutorService executorService){
//
//  }
//
//}
