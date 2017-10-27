package com.calmeter.core.food.model.nutrient.macro.carb;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.Table;

@Entity
@Table(name = "consolidated_carbs")
public class ConsolidatedCarbs implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@Column(precision = 10, scale = 2)
	private Double fiber;

	@Column(precision = 10, scale = 2)
	private Double starch;

	@Column(precision = 10, scale = 2)
	private Double sugarAlcohol;

	@Column(precision = 10, scale = 2)
	private Double sugar;

	@Column(precision = 10, scale = 2)
	private Double total;

	private Map<SugarLabel, Double> sugarMap;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getFiber() {
		return fiber;
	}

	public void setFiber(Double fiber) {
		this.fiber = fiber;
	}

	public Double getStarch() {
		return starch;
	}

	public void setStarch(Double starch) {
		this.starch = starch;
	}

	public Double getSugarAlcohol() {
		return sugarAlcohol;
	}

	public void setSugarAlcohol(Double sugarAlcohol) {
		this.sugarAlcohol = sugarAlcohol;
	}

	public Double getSugar() {
		return sugar;
	}

	public void setSugar(Double sugar) {
		this.sugar = sugar;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "carb_sugar_values", joinColumns = @JoinColumn(name = "consolidated_carbs_id"))
	@MapKeyEnumerated(EnumType.STRING)
	@Column(name = "value")
	public Map<SugarLabel, Double> getSugarMap() {
		return sugarMap;
	}

	public void setSugarMap(Map<SugarLabel, Double> sugarMap) {
		this.sugarMap = sugarMap;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

}
