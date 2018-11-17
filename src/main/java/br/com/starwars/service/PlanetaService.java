package br.com.starwars.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.mapping.CrudMethodsSupportedHttpMethods;
import org.springframework.stereotype.Service;

import br.com.starwars.exception.ObjectNotFoundException;
import br.com.starwars.models.Planeta;
import br.com.starwars.repositorie.PlanetaRepositorie;

@Service
public class PlanetaService {

	@Autowired
	private PlanetaRepositorie repoPlanet;

	public Planeta findById(String id) {
		Optional<Planeta> objPlaneta = repoPlanet.findById(id);
		return objPlaneta.orElseThrow(() -> new ObjectNotFoundException(
				"Planeta não encontrado! Id: " + id + ", Tipo: " + Planeta.class.getName()));
	}

	public Planeta findByName(String name) {

		return repoPlanet.findByName(name);
	}

	public Planeta inserir(Planeta objPlaneta) {
		// estou colocando um objeto novo entao o id precisa ser null
		objPlaneta.setId(null);
		return repoPlanet.save(objPlaneta);
	}

	public Planeta alterar(Planeta objPlaneta) throws ObjectNotFoundException {

		Planeta objPlanetaEncontrado = findById(objPlaneta.getId());
		objPlanetaEncontrado.setName(objPlaneta.getName());

		return repoPlanet.save(objPlanetaEncontrado);
	}

	public void excluir(String id) {
		repoPlanet.deleteById(id);
	}

	public List<Planeta> listaPlanetas() {
		return repoPlanet.findAll();
	}

}
