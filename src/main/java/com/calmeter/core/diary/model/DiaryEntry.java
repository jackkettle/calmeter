package com.calmeter.core.diary.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.calmeter.core.account.model.User;
import com.calmeter.core.diary.controller.DiaryEntryDeserializer;
import com.calmeter.core.diary.utils.DiaryEntryHelper;
import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.Meal;
import com.calmeter.core.food.model.nutrient.NutritionalInformation;
import com.calmeter.core.json.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "diary_entry")
@JsonDeserialize(using = DiaryEntryDeserializer.class)
public class DiaryEntry {

	private Long id;

	private User user;

	private List<Meal> meals;

	private List<FoodItem> foodItems;

	private LocalDateTime time;

	private boolean eatan;

	private String description;

	private NutritionalInformation totalNutrionalnformation;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId () {
		return id;
	}

	public void setId (Long id) {
		this.id = id;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public User getUser () {
		return user;
	}

	public void setUser (User user) {
		this.user = user;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "diary_meal_map", joinColumns = @JoinColumn(name = "diary_id"), inverseJoinColumns = @JoinColumn(name = "meal_id"))
	public List<Meal> getMeals () {
		return this.meals;
	}

	public void setMeals (List<Meal> meal) {
		this.meals = meal;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "diary_food_item_map", joinColumns = @JoinColumn(name = "diary_id"), inverseJoinColumns = @JoinColumn(name = "food_item_id"))
	public List<FoodItem> getFoodItems () {
		return foodItems;
	}

	public void setFoodItems (List<FoodItem> foodItem) {
		this.foodItems = foodItem;
	}

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	public LocalDateTime getTime () {
		return time;
	}

	public void setTime (LocalDateTime time) {
		this.time = time;
	}

	public boolean isEatan () {
		return eatan;
	}

	public void setEatan (boolean eatan) {
		this.eatan = eatan;
	}

	public String getDescription () {
		return description;
	}

	public void setDescription (String description) {
		this.description = description;
	}

	@Transient
	public NutritionalInformation getTotalNutrionalnformation () {
		return totalNutrionalnformation;
	}

	public void setTotalNutrionalnformation (NutritionalInformation totalNutrionalnformation) {
		this.totalNutrionalnformation = totalNutrionalnformation;
	}

	public void computeNutritionalInformation () {
		this.setTotalNutrionalnformation (DiaryEntryHelper.computeNutritionalInformation (this));
	}

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger (DiaryEntry.class);

}
