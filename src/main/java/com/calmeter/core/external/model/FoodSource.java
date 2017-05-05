package com.calmeter.core.external.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "food_source")
public class FoodSource {

	private Long id;
	
	private String name;
	
	private String apiKey;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName () {
		return name;
	}
	public void setName (String name) {
		this.name = name;
	}

	public String getApiKey () {
		return apiKey;
	}

	public void setApiKey (String apiKey) {
		this.apiKey = apiKey;
	}
	
}
