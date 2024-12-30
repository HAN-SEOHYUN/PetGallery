package com.example.wedlessInvite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WedlessInviteApplication {
	public static void main(String[] args) {
		SpringApplication.run(WedlessInviteApplication.class, args);
	}
}
