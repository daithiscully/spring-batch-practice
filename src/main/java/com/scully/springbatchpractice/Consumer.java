package com.scully.springbatchpractice;

import com.scully.springbatchpractice.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Consumer {

  @KafkaListener(topics = "people", groupId = "people-group")
  public void consume(Person person) {
    log.info("#### -> Consumed message -> {}", person);
  }
}