package com.scully.springbatchpractice.config;

import com.scully.springbatchpractice.listener.JobCompletionNotificationListener;
import com.scully.springbatchpractice.model.Person;
import com.scully.springbatchpractice.processor.PersonItemProcessor;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfiguration {

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  private FlatFileItemReader<Person> reader() {
    BeanWrapperFieldSetMapper<Person> mapper = new BeanWrapperFieldSetMapper<>();
    mapper.setTargetType(Person.class);
    return new FlatFileItemReaderBuilder<Person>()
        .name("personItemReader")
        .resource(new ClassPathResource("sample-data.csv"))
        .delimited()
        .names(new String[]{"firstName", "lastName"})
        .fieldSetMapper(mapper)
        .build();
  }

  @Bean
  public JdbcBatchItemWriter<Person> writer(DataSource dataSource) {
    return new JdbcBatchItemWriterBuilder<Person>()
        .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
        .sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)")
        .dataSource(dataSource)
        .build();
  }

  @Bean
  public Job importUserJob(JobCompletionNotificationListener listener, Step step) {
    return jobBuilderFactory.get("importUserJob")
        .incrementer(new RunIdIncrementer())
        .listener(listener)
        .flow(step)
        .end()
        .build();
  }

  @Bean
  public PersonItemProcessor processor() {
    return new PersonItemProcessor();
  }

  @Bean
  public Step step(JdbcBatchItemWriter<Person> writer) {
    return stepBuilderFactory.get("step1")
        .<Person, Person>chunk(10)
        .reader(reader())
        .processor(processor())
        .writer(writer)
        .build();
  }
}