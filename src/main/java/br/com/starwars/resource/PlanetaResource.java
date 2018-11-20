package br.com.starwars.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.starwars.exception.ObjectNotFoundException;
import br.com.starwars.models.Planeta;
import br.com.starwars.service.PlanetaService;
/**
 * Classe que irá cuidar de todas as requisições rest
 * @author Danilo
 *
 */
@RestController
@RequestMapping("/planeta")
public class PlanetaResource {

	@Autowired
	private PlanetaService service;
	
	

	@RequestMapping(path = "/mensagem/",method = RequestMethod.GET)
	public String teste() {

		return "Planeta não encontrado";
	}
	
	/**
	 * Retorna uma lista de planetas
	 * 
	 * URI: localhost:8080/planeta
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Planeta>> listar() {

		List<Planeta> listPlaneta = service.listaPlanetas();

		return ResponseEntity.ok().body(listPlaneta);
	}
	
	/**Método que retorna um planeta pelo id
	 * 
	 * URI: localhost:8080/planeta/busca/id
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(path = "/busca/{id}", method = RequestMethod.GET)
	public ResponseEntity<Planeta> findById(@PathVariable String id) {
		Planeta objPlaneta = service.findById(id);
		return ResponseEntity.ok().body(objPlaneta);
	}
	
	/**
	 * Retorn um planeta pelo nome
	 * 
	 * URI: localhost:8080/planeta/nome
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public ResponseEntity<Planeta> findByName(@PathVariable String name) {
		Planeta objPlaneta = service.findByName(name);
		return ResponseEntity.ok().body(objPlaneta);
	}
	
	/**
	 * Inseri um planeta
	 * 
	 * URI: localhost:8080/planeta/
	 * 
	 * @param objPlaneta
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> inserir(@RequestBody Planeta objPlaneta) {

		objPlaneta = service.inserir(objPlaneta);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objPlaneta.getId())
				.toUri();

		//retorna uma uri com o id
		return ResponseEntity.created(uri).build();
	}
	
	/**
	 * Altera um planeta pelo id
	 * 
	 * URI: localhost:8080/planeta/id
	 * @param objPlaneta
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> alterar(@RequestBody Planeta objPlaneta, @PathVariable String id) {

		objPlaneta.setId(id);

		objPlaneta = service.alterar(objPlaneta);

		return ResponseEntity.noContent().build();
	}
	
	/**
	 * Deleta um planeta pelo id
	 * 
	 * URI: localhost:8080/planeta/id
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity excluir(@PathVariable String id) {

		Planeta planeta = service.findById(id);

		if (planeta == null) {
			throw new ObjectNotFoundException("Planeta não encontrado");
		} else {

			service.excluir(id);

			return new ResponseEntity<>("Planeta deletado com sucesso!!", HttpStatus.OK);
		}
	}

}
