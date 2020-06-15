package org.example.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.example.common.service.StudentService;

@Configuration
public class StudentClientConfig {
  @Bean
  RmiProxyFactoryBean rmiStudentProxyFactoryBean() {
    RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
    rmiProxyFactoryBean.setServiceInterface(StudentService.class);
    rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/StudentService");
    return rmiProxyFactoryBean;
  }
}
