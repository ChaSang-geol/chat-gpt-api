package com.openai.chatgpt;

import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.jobrunr.autoconfigure.JobRunrAutoConfiguration;

@SpringBootApplication
// @EnableAutoConfiguration(exclude = { JobRunrAutoConfiguration.class})
public class ChatGptApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatGptApplication.class, args);
	}

}
