package com.example.floki;

import com.example.floki.tools.EnvLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlokiApplication {

	public static void main(String[] args) {
		EnvLoader.loadEnv();
		SpringApplication.run(FlokiApplication.class, args);
	}

}
