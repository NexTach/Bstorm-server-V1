package com.nextech.server.v1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories(basePackages = "com.nextech.server.v1.global.security.jwt.repository")
public class BstormServerV1Application {

	public static void main(String[] args) {
		SpringApplication.run(BstormServerV1Application.class, args);
	}
}
