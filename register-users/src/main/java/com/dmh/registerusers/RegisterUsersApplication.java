package com.dmh.registerusers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "com.dmh.registerusers")
@EnableJpaAuditing
public class RegisterUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegisterUsersApplication.class, args);
	}

}
