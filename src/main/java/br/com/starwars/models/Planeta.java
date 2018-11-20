package br.com.starwars.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Document(collection = "planeta")
@NoArgsConstructor
@AllArgsConstructor
public class Planeta {

	public Planeta(String name, String climate, String terrain) {
		super();
		this.name = name;
		this.climate = climate;
		this.terrain = terrain;
	}

	@Id
	@JsonProperty("id")
	private String id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("climate")
	private String climate;

	@JsonProperty("terrain")
	private String terrain;

	@SuppressWarnings("rawtypes")
	@JsonProperty("films")
	private List films;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClimate() {
		return climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	public String getTerrain() {
		return terrain;
	}

	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}

	@SuppressWarnings("rawtypes")
	public List getFilms() {
		return films;
	}

	@SuppressWarnings("rawtypes")
	public void setFilms(List films) {
		this.films = films;
	}

}
