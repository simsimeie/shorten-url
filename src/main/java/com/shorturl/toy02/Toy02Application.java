package com.shorturl.toy02;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Slf4j
public class Toy02Application{
    public static void main(String[] args) {
        SpringApplication.run(Toy02Application.class, args);
    }
}



