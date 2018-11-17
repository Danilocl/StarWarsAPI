package br.com.starwars.initializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;

import br.com.starwars.models.Planeta;

@Component
public class Init implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(Init.class);

	private final MongoTemplate mongoTemplate;

	@Autowired
	public Init(MongoTemplate mongoTemplate) {

		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public void run(String... strings) throws Exception {

		Planeta planeta;

		LOGGER.info("Inserindo 10 planetas no MongoDB");

		for (int i = 1; i <= 10; i++) {

			String planetString = Unirest.get("https://swapi.co/api/planets/{id}").routeParam("id", String.valueOf(i))
					.asJson().getBody().toString();

			planeta = new Gson().fromJson(planetString, Planeta.class);

			mongoTemplate.save(planeta, "planeta");

		}

	}

}
