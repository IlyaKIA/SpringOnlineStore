package org.example.context.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"org.example.context.repository", "org.example.context.service"})
public class AppConfig {
}
