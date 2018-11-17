package br.com.starwars.repositorie;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import br.com.starwars.models.Planeta;

@RestController
@Repository
public interface PlanetaRepositorie extends MongoRepository<Planeta, String> {

	Planeta findByName(String name);

}
