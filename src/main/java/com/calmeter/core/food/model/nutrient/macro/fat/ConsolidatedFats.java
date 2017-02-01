package com.calmeter.core.food.model.nutrient.macro.fat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "consolidated_fats")
public class ConsolidatedFats {

	private Long id;

	@Column(precision = 10, scale = 2)
	private Double saturatedFat;

	@Column(precision = 10, scale = 2)
	private Double monoUnsaturatedFat;
	
	@Column(precision = 10, scale = 2)
	private Double polyUnsaturatedFat;
	
	@Column(precision = 10, scale = 2)
	private Double transFat;
	
	@Column(precision = 10, scale = 2)
	private Double cholesterol;

	@Column(precision = 10, scale = 2)
	private Double totalFat;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId () {
		return id;
	}

	public void setId (Long id) {
		this.id = id;
	}

	
	public Double getSaturatedFat () {
		return saturatedFat;
	}

	
	public void setSaturatedFat (Double saturatedFat) {
		this.saturatedFat = saturatedFat;
	}

	
	public Double getMonoUnsaturatedFat () {
		return monoUnsaturatedFat;
	}

	
	public void setMonoUnsaturatedFat (Double monoUnsaturatedFat) {
		this.monoUnsaturatedFat = monoUnsaturatedFat;
	}

	
	public Double getPolyUnsaturatedFat () {
		return polyUnsaturatedFat;
	}

	
	public void setPolyUnsaturatedFat (Double polyUnsaturatedFat) {
		this.polyUnsaturatedFat = polyUnsaturatedFat;
	}

	
	public Double getTransFat () {
		return transFat;
	}

	
	public void setTransFat (Double transFat) {
		this.transFat = transFat;
	}
	
	public Double getCholesterol () {
		return cholesterol;
	}

	
	public void setCholesterol (Double cholesterol) {
		this.cholesterol = cholesterol;
	}

	
	public Double getTotalFat () {
		return totalFat;
	}

	
	public void setTotalFat (Double totalFat) {
		this.totalFat = totalFat;
	}

}
