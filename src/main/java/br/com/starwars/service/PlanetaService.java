package br.com.starwars.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.starwars.exception.ObjectNotFoundException;
import br.com.starwars.models.Planeta;
import br.com.starwars.repository.PlanetaRepository;

/**
 * Classe que atua como controller, acessando todos os métodos do
 * PlanetaRepository
 * 
 * @author Danilo
 *
 */
@Service
public class PlanetaService {

	@Autowired
	private PlanetaRepository repoPlanet;

	/**
	 * Busca pelo id
	 * 
	 * @param id
	 * @return
	 */
	public Planeta findById(String id) {
		Optional<Planeta> objPlaneta = repoPlanet.findById(id);
		return objPlaneta.orElseThrow(() -> new ObjectNotFoundException(
				"Planeta não encontrado! Id: " + id + ", Tipo: " + Planeta.class.getName()));
	}

	/**
	 * Busca pelo nome
	 * 
	 * @param name
	 * @return
	 * @throws ObjectNotFoundException
	 */
	public Planeta findByName(String name) throws ObjectNotFoundException {

		return repoPlanet.findByName(name);
	}

	/**
	 * Inseri
	 * 
	 * @param objPlaneta
	 * @return
	 */
	public Planeta inserir(Planeta objPlaneta) {
		// estou colocando um objeto novo entao o id precisa ser null
		objPlaneta.setId(null);
		return repoPlanet.save(objPlaneta);
	}

	/**
	 * Altera um planeta pelo id
	 * 
	 * @param objPlaneta
	 * @return
	 * @throws ObjectNotFoundException
	 */
	public Planeta alterar(Planeta objPlaneta) throws ObjectNotFoundException {

		Planeta objPlanetaEncontrado = findById(objPlaneta.getId());
		objPlanetaEncontrado.setName(objPlaneta.getName());

		return repoPlanet.save(objPlanetaEncontrado);
	}

	/**
	 * Deleta um planeta pelo id
	 * 
	 * @param id
	 */
	public void excluir(String id) {

		repoPlanet.deleteById(id);

	}

	/**
	 * Lista todos os planetas
	 * 
	 * @return
	 */
	public List<Planeta> listaPlanetas() {
		return repoPlanet.findAll();
	}

}
