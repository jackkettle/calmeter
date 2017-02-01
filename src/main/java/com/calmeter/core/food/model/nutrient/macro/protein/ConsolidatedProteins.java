package com.calmeter.core.food.model.nutrient.macro.protein;

import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.Table;

@Entity
@Table(name = "consolidated_proteins")
public class ConsolidatedProteins {

	private Long id;

	@Column(precision = 10, scale = 2)
	private Double protein;

	private Map<ProteinLabel, Double> proteinMap;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId () {
		return id;
	}

	public void setId (Long id) {
		this.id = id;
	}

	public Double getProtein () {
		return protein;
	}

	public void setProtein (Double protein) {
		this.protein = protein;
	}

	@ElementCollection
	@CollectionTable(name = "protein_values", joinColumns = @JoinColumn(name = "consolidated_proteins_id"))
	@MapKeyEnumerated(EnumType.STRING)
	@Column(name = "value")
	public Map<ProteinLabel, Double> getProteinMap () {
		return proteinMap;
	}

	public void setProteinMap (Map<ProteinLabel, Double> proteinMap) {
		this.proteinMap = proteinMap;
	}

}
