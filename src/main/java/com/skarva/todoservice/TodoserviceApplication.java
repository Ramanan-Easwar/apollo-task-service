package com.skarva.todoservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TodoserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoserviceApplication.class, args);
	}

	@Bean
	public Gson getGson(GsonBuilder builder) {
		return builder.setPrettyPrinting().create();
	}

}
