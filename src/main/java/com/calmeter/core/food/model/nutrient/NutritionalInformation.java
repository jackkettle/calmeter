package com.calmeter.core.food.model.nutrient;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.nutrient.micro.MineralLabel;
import com.calmeter.core.food.model.nutrient.micro.VitaminLabel;

import javax.persistence.JoinColumn;

@Entity
@Table(name = "nutritional_info")
public class NutritionalInformation {

	protected Long id;

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

	private Map<VitaminLabel, Double> vitaminMap;

	private Map<MineralLabel, Double> mineralMap;
	
	private FoodItem foodItem;

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

    @ElementCollection
    @CollectionTable(name="vitamin_values", joinColumns=@JoinColumn(name="nutritional_info_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name="value")
	public Map<VitaminLabel, Double> getVitaminMap() {
		return vitaminMap;
	}

	public void setVitaminMap(Map<VitaminLabel, Double> vitaminMap) {
		this.vitaminMap = vitaminMap;
	}

    @ElementCollection
    @CollectionTable(name="mineral_values", joinColumns=@JoinColumn(name="nutritional_info_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name="value")
	public Map<MineralLabel, Double> getMineralMap() {
		return mineralMap;
	}

	public void setMineralMap(Map<MineralLabel, Double> mineralMap) {
		this.mineralMap = mineralMap;
	}

    @OneToOne(mappedBy="nutritionalInformation")
	public FoodItem getFoodItem() {
		return foodItem;
	}

	public void setFoodItem(FoodItem foodItem) {
		this.foodItem = foodItem;
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
		this.vitaminMap = new HashMap<VitaminLabel, Double>();
		this.mineralMap = new HashMap<MineralLabel, Double>();
	}
}
