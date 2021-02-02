package com.maddy8381.PersonalProjectMngmTool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class PersonalProjectMngmToolApplication {

	@Bean //We are creating Bean of bCryptPassEncoder to pass it to UserService.java
	BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(PersonalProjectMngmToolApplication.class, args);
	}

}
