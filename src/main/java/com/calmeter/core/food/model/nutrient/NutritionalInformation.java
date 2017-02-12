package com.calmeter.core.food.model.nutrient;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.nutrient.macro.carb.ConsolidatedCarbs;
import com.calmeter.core.food.model.nutrient.macro.fat.ConsolidatedFats;
import com.calmeter.core.food.model.nutrient.macro.protein.ConsolidatedProteins;
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
	private Double servingSize;

	ConsolidatedCarbs consolidatedCarbs;

	ConsolidatedFats consolidatedFats;

	ConsolidatedProteins consolidatedProteins;

	private Map<VitaminLabel, Double> vitaminMap;

	private Map<MineralLabel, Double> mineralMap;
	
	private Integer caffeine;

	private FoodItem foodItem;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId () {
		return id;
	}

	public void setId (Long id) {
		this.id = id;
	}

	public double getCalories () {
		return calories;
	}

	public void setCalories (Double calories) {
		this.calories = calories;
	}

	public Double getServingSize() {
		return servingSize;
	}

	public void setServingSize(Double servingSize) {
		this.servingSize = servingSize;
	}

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "consolidated_carbs_id")
	public ConsolidatedCarbs getConsolidatedCarbs () {
		return consolidatedCarbs;
	}

	public void setConsolidatedCarbs (ConsolidatedCarbs consolidatedCarbs) {
		this.consolidatedCarbs = consolidatedCarbs;
	}

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "consolidated_fats_id")
	public ConsolidatedFats getConsolidatedFats () {
		return consolidatedFats;
	}

	public void setConsolidatedFats (ConsolidatedFats consolidatedFats) {
		this.consolidatedFats = consolidatedFats;
	}

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "consolidated_proteins_id")
	public ConsolidatedProteins getConsolidatedProteins () {
		return consolidatedProteins;
	}

	public void setConsolidatedProteins (ConsolidatedProteins consolidatedProteins) {
		this.consolidatedProteins = consolidatedProteins;
	}

	@ElementCollection
	@CollectionTable(name = "vitamin_values", joinColumns = @JoinColumn(name = "nutritional_info_id"))
	@MapKeyEnumerated(EnumType.STRING)
	@Column(name = "value")
	public Map<VitaminLabel, Double> getVitaminMap () {
		return vitaminMap;
	}

	public void setVitaminMap (Map<VitaminLabel, Double> vitaminMap) {
		this.vitaminMap = vitaminMap;
	}

	@ElementCollection
	@CollectionTable(name = "mineral_values", joinColumns = @JoinColumn(name = "nutritional_info_id"))
	@MapKeyEnumerated(EnumType.STRING)
	@Column(name = "value")
	public Map<MineralLabel, Double> getMineralMap () {
		return mineralMap;
	}

	public void setMineralMap (Map<MineralLabel, Double> mineralMap) {
		this.mineralMap = mineralMap;
	}

	public Integer getCaffeine() {
		return caffeine;
	}

	public void setCaffeine(Integer caffeine) {
		this.caffeine = caffeine;
	}

	@OneToOne(mappedBy = "nutritionalInformation")
	public FoodItem getFoodItem () {
		return foodItem;
	}

	public void setFoodItem (FoodItem foodItem) {
		this.foodItem = foodItem;
	}

	public NutritionalInformation () {
		this.calories = 0.0;
		this.consolidatedCarbs = new ConsolidatedCarbs ();
		this.consolidatedFats = new ConsolidatedFats ();
		this.consolidatedProteins = new ConsolidatedProteins ();
		this.vitaminMap = new HashMap<VitaminLabel, Double> ();
		this.mineralMap = new HashMap<MineralLabel, Double> ();
	}
}
