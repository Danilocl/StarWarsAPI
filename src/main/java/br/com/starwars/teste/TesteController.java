package br.com.starwars.teste;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.starwars.StarWarsApiApplication;
import br.com.starwars.models.Planeta;
import br.com.starwars.repository.PlanetaRepository;

/**
 * Classe de testes
 * 
 * obs: Tive problemas com o mongodb no windows, então não pude testar
 * corretamente. Acredito estar correto
 * 
 * @author Danilo
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StarWarsApiApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
public class TesteController {

	private MediaType content = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	@SuppressWarnings("rawtypes")
	private HttpMessageConverter message;

	@Autowired
	private PlanetaRepository planetaRepository;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(planetaRepository).build();
	}

	/**
	 * Converte para json
	 * 
	 * @param o
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	protected String converteJson(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.message.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

	@Test
	public void testeBuscaPorId() throws Exception {

		Planeta planeta = getPlanet();

		this.mockMvc.perform(get("/planeta/busca?id=" + planeta.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(content)).andExpect(jsonPath("$.id", is(planeta.getId())))
				.andExpect(jsonPath("$.name", is(planeta.getName())))
				.andExpect(jsonPath("$.climate", is(planeta.getClimate())))
				.andExpect(jsonPath("$.films", is(planeta.getFilms())))
				.andExpect(jsonPath("$.terrain", is(planeta.getTerrain())));
	}

	@Test
	public void testeBuscaNome() throws Exception {

		Planeta planeta = getPlanet();

		this.mockMvc.perform(get("/planeta?name=" + planeta.getName())).andExpect(status().isOk())
				.andExpect(content().contentType(content)).andExpect(jsonPath("$.id", is(planeta.getId())))
				.andExpect(jsonPath("$.name", is(planeta.getName())))
				.andExpect(jsonPath("$.climate", is(planeta.getClimate())))
				.andExpect(jsonPath("$.films", is(planeta.getFilms())))
				.andExpect(jsonPath("$.terrain", is(planeta.getTerrain())));
	}

	@Test
	public void testeExclusao() throws Exception {

		Planeta getPlanet = getPlanet();

		this.mockMvc.perform(delete("/planeta?id=" + getPlanet.getId()).contentType(content))
				.andExpect(status().isNoContent());

	}

	@Test
	public void testeListarPlanetas() throws Exception {

		Planeta[] planetas = new Planeta[] { retornaPlanetaValido("Tatooine"), retornaPlanetaValido("Alderaan") };
		String planetsJson = converteJson(planetas);

		this.mockMvc.perform(get("/planeta").contentType(content)).andExpect(status().isOk())
				.andExpect(content().contentType(content)).andExpect(content().json(planetsJson));
	}

	@Test
	public void testeInsercao() throws Exception {

		Planeta planetaValido = getPlanet();
		String planetName = planetaValido.getName();
		List films;
		films = planetaValido.getFilms();
		String terrain = planetaValido.getTerrain();
		String climate = planetaValido.getClimate();

		planetaRepository.deleteById(planetName);

		String json = converteJson(new Planeta(planetName, climate, terrain));

		this.mockMvc.perform(post("/planeta").contentType(content).content(json)).andExpect(status().isCreated())
				.andExpect(content().contentType(content)).andExpect(jsonPath("$.name", is(planetName)))
				.andExpect(jsonPath("$.climate", is(climate))).andExpect(jsonPath("$.terrain", is(terrain)))
				.andExpect(jsonPath("$.films", is(films)));
	}

	@Test
	public void testeAlterar() throws Exception {

		Planeta planetaValido = getPlanet();
		String planetName = planetaValido.getName();
		List films;
		films = planetaValido.getFilms();
		String terrain = planetaValido.getTerrain();
		String climate = planetaValido.getClimate();

		String json = converteJson(new Planeta(planetName, climate, terrain));

		this.mockMvc.perform(put("/planeta?id=").contentType(content).content(json)).andExpect(status().isCreated())
				.andExpect(content().contentType(content)).andExpect(jsonPath("$.name", is(planetName)))
				.andExpect(jsonPath("$.climate", is(climate))).andExpect(jsonPath("$.terrain", is(terrain)))
				.andExpect(jsonPath("$.films", is(films)));
	}

	public Planeta getPlanet() {

		String nome = "Tatooine";
		return retornaPlanetaValido(nome);
	}

	public Planeta retornaPlanetaValido(String nome) {
		Planeta planetaValido;

		planetaValido = planetaRepository.findByName(nome);

		return planetaValido;
	}
}
