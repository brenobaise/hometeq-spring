package com.brenobaise.hometeq_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;


@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@SpringBootApplication
public class HometeqSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(HometeqSpringApplication.class, args);
	}

}
