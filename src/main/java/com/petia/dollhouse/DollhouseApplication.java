package com.petia.dollhouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DollhouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(DollhouseApplication.class, args);
    }

}
