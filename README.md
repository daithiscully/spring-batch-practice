# Spring Batch Example

A simple Spring batch program that loads in data from a CSV (flat) file and stores it into a database

Follows the guide (roughly) found [here](https://spring.io/guides/gs/batch-processing/)

## Requirements
* MySQL / MariaDB
  * Create a schema in the database called: spring-batch (Or modify the application.properties file) 
  * Create a table called "people" (use the src/main/resources/schema.sql file to do so)
* Java 12


