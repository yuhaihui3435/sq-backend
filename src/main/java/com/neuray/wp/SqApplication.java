package com.neuray.wp;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SqApplication {

	public static void main(String[] args) {
		SpringApplication app=new SpringApplication(SqApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

}

