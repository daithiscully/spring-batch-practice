package com.scully.springbatchpractice;

import com.scully.springbatchpractice.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Producer {

  private static final String TOPIC = "people";

  private final KafkaTemplate<String, Person> kafkaTemplate;

  public void sendMessage(Person person) {
    log.info("#### -> Producing message -> {}", person);
    this.kafkaTemplate.sendDefault(person);
  }
}