package br.com.starwars.resource;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.starwars.models.Planeta;
import br.com.starwars.service.PlanetaService;

@RestController
@RequestMapping("/planeta")
public class PlanetaResource {

	@Autowired
	private PlanetaService service;

//	@RequestMapping(method = RequestMethod.GET)
//	public String teste() {
//
//		return "Ta funcionando";
//	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Planeta>> listar() {

		List<Planeta> listPlaneta = service.listaPlanetas();

		return ResponseEntity.ok().body(listPlaneta);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Planeta> findById(@PathVariable String id) {
		Planeta objPlaneta = service.findById(id);
		return ResponseEntity.ok().body(objPlaneta);
	}

	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public ResponseEntity<Planeta> findByName(@PathVariable String name) {
		Planeta objPlaneta = service.findByName(name);
		return ResponseEntity.ok().body(objPlaneta);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> inserir(@RequestBody Planeta objPlaneta) {

		objPlaneta = service.inserir(objPlaneta);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objPlaneta.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> alterar(@RequestBody Planeta objPlaneta, @PathVariable String id) {

		objPlaneta.setId(id);

		objPlaneta = service.alterar(objPlaneta);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluir(@PathVariable String id) {

		service.excluir(id);

		return ResponseEntity.noContent().build();
	}

}
