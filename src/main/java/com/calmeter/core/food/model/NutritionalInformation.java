package com.calmeter.core.food.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "nutritional_info")
public class NutritionalInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(precision = 10, scale = 2)
	private Double calories;

	@Column(precision = 10, scale = 2)
	private Double saturatedFat;

	@Column(precision = 10, scale = 2)
	private Double transFat;

	@Column(precision = 10, scale = 2)
	private Double carbohydrate;

	@Column(precision = 10, scale = 2)
	private Double sugars;

	@Column(precision = 10, scale = 2)
	private Double fibre;

	@Column(precision = 10, scale = 2)
	private Double protein;

	@Column(precision = 10, scale = 2)
	private Double salt;

	@ElementCollection
	@MapKeyColumn(name = "name")
	@Column(name = "value")
	@CollectionTable(name = "vitamin_values", joinColumns = @JoinColumn(name = "id"))
	private Map<String, Double> vitaminMap;

	@ElementCollection
	@MapKeyColumn(name = "name")
	@Column(name = "value")
	@CollectionTable(name = "mineral_values", joinColumns = @JoinColumn(name = "id"))
	private Map<String, Double> mineralMap;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getCalories() {
		return calories;
	}

	public void setCalories(double calories) {
		this.calories = calories;
	}

	public double getSaturatedFat() {
		return saturatedFat;
	}

	public void setSaturatedFat(double saturatedFat) {
		this.saturatedFat = saturatedFat;
	}

	public double getTransFat() {
		return transFat;
	}

	public void setTransFat(double transFat) {
		this.transFat = transFat;
	}

	public double getCarbohydrate() {
		return carbohydrate;
	}

	public void setCarbohydrate(double carbohydrate) {
		this.carbohydrate = carbohydrate;
	}

	public double getSugars() {
		return sugars;
	}

	public void setSugars(double sugars) {
		this.sugars = sugars;
	}

	public double getFibre() {
		return fibre;
	}

	public void setFibre(double fibre) {
		this.fibre = fibre;
	}

	public double getProtein() {
		return protein;
	}

	public void setProtein(double protein) {
		this.protein = protein;
	}

	public double getSalt() {
		return salt;
	}

	public void setSalt(double salt) {
		this.salt = salt;
	}

	public NutritionalInformation() {
		this.calories = 0.0;
		this.saturatedFat = 0.0;
		this.transFat = 0.0;
		this.carbohydrate = 0.0;
		this.sugars = 0.0;
		this.fibre = 0.0;
		this.protein = 0.0;
		this.salt = 0.0;
	}
}
