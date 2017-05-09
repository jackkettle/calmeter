package com.calmeter.core.food.source.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.calmeter.core.food.source.handler.IFoodSourceHandler;

@Entity
@Table(name = "food_source")
public class FoodSource {

	private Long id;

	private String name;

	private boolean externalSource;

	private String apiKey;

	private String baseURI;

	private Class<IFoodSourceHandler> sourceHandler;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId () {
		return id;
	}

	public void setId (Long id) {
		this.id = id;
	}

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public boolean isExternalSource () {
		return externalSource;
	}

	public void setExternalSource (boolean externalSource) {
		this.externalSource = externalSource;
	}

	public String getApiKey () {
		return apiKey;
	}

	public void setApiKey (String apiKey) {
		this.apiKey = apiKey;
	}

	public String getBaseURI () {
		return baseURI;
	}

	public void setBaseURI (String baseURI) {
		this.baseURI = baseURI;
	}

	public Class<IFoodSourceHandler> getSourceHandler () {
		return sourceHandler;
	}

	public void setSourceHandler (Class<IFoodSourceHandler> sourceHandler) {
		this.sourceHandler = sourceHandler;
	}

}
