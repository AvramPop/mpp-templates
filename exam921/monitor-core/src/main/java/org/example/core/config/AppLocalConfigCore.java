package org.example.core.config;

import org.example.server.repository.SensorRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan({"org.example.server"})
public class AppLocalConfigCore {
}
