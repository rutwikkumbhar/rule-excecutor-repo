package com.monocept.ruleexecutor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RuleExecutorApplication {

	public static void main(String[] args) {
		SpringApplication.run(RuleExecutorApplication.class, args);
	}

}
