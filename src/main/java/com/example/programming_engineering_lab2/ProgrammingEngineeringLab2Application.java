package com.example.programming_engineering_lab2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProgrammingEngineeringLab2Application {

	public static void main(String[] args) {
		SpringApplication.run(ProgrammingEngineeringLab2Application.class, args);
	}

}
