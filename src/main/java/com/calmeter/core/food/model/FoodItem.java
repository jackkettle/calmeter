package com.calmeter.core.food.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.calmeter.core.account.model.User;
import com.calmeter.core.food.controller.FoodItemDeserializer;
import com.calmeter.core.food.model.nutrient.NutritionalInformation;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity
@Table(name = "food_item")
@JsonDeserialize(using = FoodItemDeserializer.class)
public class FoodItem implements IFood, Serializable {

	private static final long serialVersionUID = 1L;

	protected Long id;

	private Long externalId;

	private String name;

	private String description;

	@JsonManagedReference
	private NutritionalInformation nutritionalInformation;

	private User creator;

	private Set<Meal> meals;

	private FoodItemType foodItemType;

	public FoodItem() {
		this.foodItemType = FoodItemType.USER_ITEM;
	}

	public FoodItem(FoodItemType foodItemType) {
		this.foodItemType = foodItemType;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "external_id", nullable = true, unique = true)
	public Long getExternalId() {
		return externalId;
	}

	public void setExternalId(Long externalId) {
		this.externalId = externalId;
	}

	@Column(name = "name", nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", nullable = true, length = 2000)
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

	@ManyToOne
	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	@ManyToMany(mappedBy = "ingredients")
	public Set<Meal> getMeals() {
		return meals;
	}

	public void setMeals(Set<Meal> meals) {
		this.meals = meals;
	}

	@Enumerated(EnumType.STRING)
	public FoodItemType getFoodItemType() {
		return foodItemType;
	}

	public void setFoodItemType(FoodItemType foodItemType) {
		this.foodItemType = foodItemType;
	}

}
