package com.calmeter.core.goals.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "nutrition_profile")
public class NutritionProfile {

	protected Long id;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId () {
		return id;
	}

	
	public void setId (Long id) {
		this.id = id;
	}
	
}
