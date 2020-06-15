package org.example.server.config;

import lombok.Builder;
import org.example.server.service.validators.StudentValidator;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySources({@PropertySource(value = "classpath:local/db.properties"),
})
public class AppConfig {
  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Bean
  public static StudentValidator studentValidator() {
    return new StudentValidator();
  }
}