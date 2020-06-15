package org.example.server.config;

import org.example.server.repository.StudentRepository;
import org.example.server.service.StudentServiceImpl;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;
import org.example.common.service.StudentService;
import org.example.server.service.validators.StudentValidator;

@Configuration
public class StudentServerConfig /*implements ApplicationContextAware */{
//  private static ApplicationContext context;
//
//  public ApplicationContext getApplicationContext() {
//    return context;
//  }

  @Bean
  RmiServiceExporter rmiStudentServiceExporter() {
    RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
    rmiServiceExporter.setServiceName("StudentService");
    rmiServiceExporter.setServiceInterface(StudentService.class);
    rmiServiceExporter.setService(studentService());
    return rmiServiceExporter;
  }

  @Bean
  StudentService studentService() {
    return new StudentServiceImpl();
//    return (StudentService) context.getBean("studentServiceImpl");
  }
//
//  @Override
//  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//    context = applicationContext;
//  }
}
