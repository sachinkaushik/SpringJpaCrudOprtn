package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SpringDemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringDemoApplication.class, args);
		
		/*User user = (User)context.getBean(User.class);
		
		System.out.println("user---- "+user);*/
		
		
		
		/*Address address = (Address)context.getBean(Address.class);
		System.out.println("user---- "+address);*/
		
	}
}

