package com.dmh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "com.dmh")
@EnableJpaAuditing
@EnableCaching
public class RegisterUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegisterUsersApplication.class, args);
	}

}
