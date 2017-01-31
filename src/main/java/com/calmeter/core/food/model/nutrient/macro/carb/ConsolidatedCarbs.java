package com.calmeter.core.food.model.nutrient.macro.carb;

import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyEnumerated;

public class ConsolidatedCarbs {

	private Long id;
	
	private Map<SugarLabel, Double> sugarMap;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    @ElementCollection
    @CollectionTable(name="carb_sugar_values", joinColumns=@JoinColumn(name="consolidated_carbs_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name="value")
	public Map<SugarLabel, Double> getSugarMap() {
		return sugarMap;
	}

	public void setSugarMap(Map<SugarLabel, Double> sugarMap) {
		this.sugarMap = sugarMap;
	}

	
}
