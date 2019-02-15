package com.wakabatimes.simplewiki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SimpleWikiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SimpleWikiApplication.class, args);
	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SimpleWikiApplication.class);
	}
}
