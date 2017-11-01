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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.calmeter.core.account.model.User;
import com.calmeter.core.diary.controller.DiaryEntryDeserializer;
import com.calmeter.core.diary.controller.FoodItemEntryListLiteSerializer;
import com.calmeter.core.diary.utils.DiaryEntryHelper;
import com.calmeter.core.food.model.FoodItemEntry;
import com.calmeter.core.food.model.Meal;
import com.calmeter.core.food.model.nutrient.NutritionalInfoType;
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

	private List<FoodItemEntry> foodItemEntries;

	private LocalDateTime dateTime;

	private boolean eatan;

	private String description;

	private NutritionalInformation totalNutrionalnformation;

	public DiaryEntry() {
		this.totalNutrionalnformation = new NutritionalInformation(NutritionalInfoType.DIARY_RECORD);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "diary_meal_map", joinColumns = @JoinColumn(name = "diary_id"), inverseJoinColumns = @JoinColumn(name = "meal_id"))
	public List<Meal> getMeals() {
		return this.meals;
	}

	public void setMeals(List<Meal> meal) {
		this.meals = meal;
	}

	@JsonSerialize(using = FoodItemEntryListLiteSerializer.class)
	@OneToMany(mappedBy = "diaryEntry")
//	@JoinTable(name = "diary_food_item_entry_map", joinColumns = @JoinColumn(name = "diary_id"), inverseJoinColumns = @JoinColumn(name = "food_item_entry_id"))
	public List<FoodItemEntry> getFoodItemEntries() {
		return foodItemEntries;
	}

	public void setFoodItemEntries(List<FoodItemEntry> foodItemEntries) {
		this.foodItemEntries = foodItemEntries;
	}

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public boolean isEatan() {
		return eatan;
	}

	public void setEatan(boolean eatan) {
		this.eatan = eatan;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "nutritional_info_id")
	public NutritionalInformation getTotalNutrionalnformation() {
		return totalNutrionalnformation;
	}

	public void setTotalNutrionalnformation(NutritionalInformation totalNutrionalnformation) {
		this.totalNutrionalnformation = totalNutrionalnformation;
	}

	public void computeNutritionalInformation() {
		this.setTotalNutrionalnformation(DiaryEntryHelper.computeNutritionalInformation(this));
	}

	public void applyServingsModifiers() {
		for (int i = 0; i < this.getFoodItemEntries().size(); i++) {
			this.getFoodItemEntries().get(i).applyServingModifier();
		}
	}

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(DiaryEntry.class);

}
