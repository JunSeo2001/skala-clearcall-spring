package com.example.clearcall;

import com.example.clearcall.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class ClearCallApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClearCallApplication.class, args);
    }
}
