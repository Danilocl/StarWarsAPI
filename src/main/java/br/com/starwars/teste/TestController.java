package br.com.starwars.teste;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.starwars.StarWarsApiApplication;
import br.com.starwars.models.Planeta;
import br.com.starwars.repository.PlanetaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StarWarsApiApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
public class TestController {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@SuppressWarnings("rawtypes")
	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	private PlanetaRepository planetaRepository;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(planetaRepository).build();
	}

	@Test(expected = NoSuchElementException.class)
	public void excluir() throws Exception  {

		Planeta getPlanet = getPlanet();

		this.mockMvc.perform(delete("/planeta/" + getPlanet.getId()).contentType(contentType))
				.andExpect(status().isNoContent());

		
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
