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

/**
 * Classe de inicialização, a mesma irá inserir os dados iniciais no banco
 * 
 * @author Danilo
 *
 */
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

		LOGGER.info("Inserindo 5 planetas iniciais no banco de dados");

		for (int i = 1; i <= 05; i++) {

			// Aqui é onde eu consulto os planetas da api SWAPI, retorno eles no formato
			// json e armazeno em uma vairável
			String planetString = Unirest.get("https://swapi.co/api/planets/{id}").routeParam("id", String.valueOf(i))
					.asJson().getBody().toString();
			
			//Aqui eu instancio a classe Planeta e passo os dados retornado como parâmetro
			planeta = new Gson().fromJson(planetString, Planeta.class);
			
			
			// Aqui é feita a inserção
			mongoTemplate.save(planeta, "planeta");

		}
		
		LOGGER.info("Inseriu!!");

	}

}
