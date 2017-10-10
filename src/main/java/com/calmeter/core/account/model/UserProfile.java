package com.calmeter.core.account.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.calmeter.core.json.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "user_profile")
public class UserProfile {

	protected Long id;
	
	private Double weight;
	
	private Double height;
	
	private Sex sex;
	
	private LocalDate dateOfBirth;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getWeight () {
		return weight;
	}

	public void setWeight (Double weight) {
		this.weight = weight;
	}
	
	public Double getHeight () {
		return height;	
	}
	
	public void setHeight (Double height) {
		this.height = height;
	}

	@Enumerated(EnumType.STRING)
	public Sex getSex () {
		return sex;
	}

	public void setSex (Sex sex) {
		this.sex = sex;
	}

	@JsonSerialize(using = LocalDateSerializer.class)
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

}
