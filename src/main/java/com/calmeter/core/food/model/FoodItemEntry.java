package com.calmeter.core.food.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.calmeter.core.diary.model.DiaryEntry;
import com.calmeter.core.food.model.nutrient.NutritionalInfoType;
import com.calmeter.core.food.model.nutrient.NutritionalInformation;
import com.calmeter.core.food.utils.FoodItemHelper;

@Entity
@Table(name = "food_item_entry")
public class FoodItemEntry {

	private Long id;

	private Double weightInGrams;

	private NutritionalInformation computedNutritionalInformation;

	private FoodItem foodItem;

	private DiaryEntry diaryEntry;

	public FoodItemEntry() {
		this.computedNutritionalInformation = new NutritionalInformation(NutritionalInfoType.READ_ONLY);
	}

	public FoodItemEntry(Double weightInGrams, FoodItem foodItem) {
		this.weightInGrams = weightInGrams;
		this.foodItem = foodItem;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getWeightInGrams() {
		return weightInGrams;
	}

	public void setWeightInGrams(Double weightInGrams) {
		this.weightInGrams = weightInGrams;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "food_item_id")
	public FoodItem getFoodItem() {
		return foodItem;
	}

	public void setFoodItem(FoodItem foodItem) {
		this.foodItem = foodItem;
	}

	@Transient
	public NutritionalInformation getComputedNutritionalInformation() {
		return computedNutritionalInformation;
	}

	public void setComputedNutritionalInformation(NutritionalInformation computedNutritionalInformation) {
		this.computedNutritionalInformation = computedNutritionalInformation;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "diary_entry_id")
	public DiaryEntry getDiaryEntry() {
		return diaryEntry;
	}

	public void setDiaryEntry(DiaryEntry diaryEntry) {
		this.diaryEntry = diaryEntry;
	}

	@Override
	public String toString() {
		return "FoodItemEntry [id=" + id + ", weightInGrams=" + weightInGrams + ", computedNutritionalInformation="
				+ computedNutritionalInformation + ", foodItem=" + foodItem + "]";
	}

	public void applyServingModifier() {
		FoodItemHelper.applyServingModifierToValues(this);
	}
}
