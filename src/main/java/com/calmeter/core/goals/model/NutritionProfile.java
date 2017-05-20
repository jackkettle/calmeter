package com.calmeter.core.goals.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.calmeter.core.food.model.nutrient.NutrionalInfoType;
import com.calmeter.core.food.model.nutrient.NutritionalInformation;

@Entity
@Table(name = "nutrition_profile")
public class NutritionProfile {

	protected Long id;
	
	private NutritionalInformation nutritionalInformation;
	
	public NutritionProfile(){
		nutritionalInformation = new NutritionalInformation(NutrionalInfoType.NUTRIONAL_PROFILE);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId () {
		return id;
	}

	public void setId (Long id) {
		this.id = id;
	}

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "nutritional_info_id")
	public NutritionalInformation getNutritionalInformation() {
		return nutritionalInformation;
	}

	public void setNutritionalInformation(NutritionalInformation nutritionalInformation) {
		this.nutritionalInformation = nutritionalInformation;
	}
	
}
