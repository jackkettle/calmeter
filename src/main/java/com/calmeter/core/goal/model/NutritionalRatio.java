package com.calmeter.core.goal.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.calmeter.core.account.model.User;
import com.calmeter.core.food.model.nutrient.NutritionalInfoType;
import com.calmeter.core.food.model.nutrient.NutritionalInformation;

@Entity
@Table(name = "nutrional_ratio")
public class NutritionalRatio {
	
	private Long id;
	
	private String name;
	
	private String description;
	
	private NutritionalInformation nutritionalInformation;
	
	private User creator;
	
	private Boolean global;
	
	public NutritionalRatio(){
		this.nutritionalInformation = new NutritionalInformation(NutritionalInfoType.RATIO);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length = 2000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "nutritional_info_id")
	public NutritionalInformation getNutritionalInformation() {
		return nutritionalInformation;
	}

	public void setNutritionalInformation(NutritionalInformation nutritionalInformation) {
		this.nutritionalInformation = nutritionalInformation;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Boolean getGlobal() {
		return global;
	}

	public void setGlobal(Boolean global) {
		this.global = global;
	}

}
