package com.lunosmart.backend.kemi;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.Timestamp;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.*;
import java.net.URLDecoder;
import java.util.Objects;
import org.springframework.ai.chat.client.ChatClient;

@SpringBootApplication

public class KemiApplication  implements WebMvcConfigurer{

	private static final Logger logger = LoggerFactory.getLogger(KemiApplication.class);

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*") // Allow requests from any origin
				.allowedMethods("GET", "POST", "PUT", "DELETE") // Allow specific methods
				.allowedHeaders("*");
	}
	public static void main(String[] args) throws IOException {
		if (FirebaseApp.getApps().isEmpty()) {
			ClassLoader classLoader = KemiApplication.class.getClassLoader();
			InputStream inputStream = classLoader.getResourceAsStream("main/serviceAccountKey.json");

			if (inputStream == null) {
				throw new IllegalArgumentException("File not found! main/serviceAccountKey.json");
			}

			FirebaseOptions options = FirebaseOptions.builder()
					.setCredentials(GoogleCredentials.fromStream(inputStream))
					.setDatabaseUrl("https://scorepandabot.firebaseio.com")
					.build();

			FirebaseApp.initializeApp(options);
		}




		SpringApplication.run(KemiApplication.class, args);
	}


}
