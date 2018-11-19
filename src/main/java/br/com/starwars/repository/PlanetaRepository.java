package br.com.starwars.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import br.com.starwars.models.Planeta;
/**
 * Repositório que irá conter os métodos de CRUD
 * 
 * @author Danilo
 *
 */
@RestController
@Repository
public interface PlanetaRepository extends MongoRepository<Planeta, String> {

	Planeta findByName(String name);

}
