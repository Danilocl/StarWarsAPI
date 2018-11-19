package br.com.starwars.teste;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;


public class MongoClientConfigForTests {

	@Value("${spring.data.mongodb.host}")
	private String HOST;

	@Value("${spring.data.mongodb.port}")
	private int PORT;

	@Bean
	public MongoClient mongoClient() {
		return new MongoClient(HOST, PORT);
	}
}
