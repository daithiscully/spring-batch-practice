package com.scully.springbatchpractice.processor;

import com.scully.springbatchpractice.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

/**
 * Capitalizes the user's names
 */
@Slf4j
public class PersonItemProcessor implements ItemProcessor<Person, Person> {

  @Override
  public Person process(final Person person) {
    var firstName = person.getFirstName().toUpperCase();
    var lastName = person.getLastName().toUpperCase();
    var transformedPerson = new Person(firstName, lastName);
    log.info("Converting ({}) into ({})", person, transformedPerson);

    return transformedPerson;
  }

}