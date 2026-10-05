package com.example.autoloc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AutolocApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutolocApplication.class, args);
    }

}
