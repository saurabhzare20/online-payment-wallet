package com.example.validateTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.RedisTemplate;






@SpringBootApplication
public class ValidateTransactionApplication {


	public static void main(String[] args) {
		SpringApplication.run(ValidateTransactionApplication.class, args);
	}

}
