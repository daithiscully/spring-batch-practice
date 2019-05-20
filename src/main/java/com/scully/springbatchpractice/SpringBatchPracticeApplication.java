package com.scully.springbatchpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class SpringBatchPracticeApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBatchPracticeApplication.class, args);
  }

}
