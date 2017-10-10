package com.calmeter.core.goal.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.calmeter.core.account.model.User;
import com.calmeter.core.food.model.nutrient.NutritionalInfoType;
import com.calmeter.core.food.model.nutrient.NutritionalInformation;
import com.calmeter.core.goal.controller.GoalProfileDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity
@Table(name = "goal_profile")
@JsonDeserialize(using = GoalProfileDeserializer.class)
public class GoalProfile {

	private Long id;
	
	private NutritionalRatio nutritionalRatio;
	
	private NutritionalInformation nutritionalInformation;

	private User user;
	
	private ActivityLevel activityLevel;
	
	private int userBMR;
	
	private WeightGoal weightGoal;
	
	private int dailyCalories;

	public GoalProfile() {
		this.nutritionalRatio = new NutritionalRatio();
		this.nutritionalInformation = new NutritionalInformation(NutritionalInfoType.GOAL);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "nutritional_ratio_id")
	public NutritionalRatio getNutritionalRatio() {
		return nutritionalRatio;
	}

	public void setNutritionalRatio(NutritionalRatio nutritionalRatio) {
		this.nutritionalRatio = nutritionalRatio;
	}
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "nutritional_info_id")
	public NutritionalInformation getNutritionalInformation() {
		return nutritionalInformation;
	}

	public void setNutritionalInformation(NutritionalInformation nutritionalInformation) {
		this.nutritionalInformation = nutritionalInformation;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Enumerated(EnumType.STRING)
	public ActivityLevel getActivityLevel() {
		return activityLevel;
	}

	public void setActivityLevel(ActivityLevel activityLevel) {
		this.activityLevel = activityLevel;
	}

	public int getUserBMR() {
		return userBMR;
	}

	public void setUserBMR(int userBMR) {
		this.userBMR = userBMR;
	}

	@Enumerated(EnumType.STRING)
	public WeightGoal getWeightGoal() {
		return weightGoal;
	}

	public void setWeightGoal(WeightGoal weightGoal) {
		this.weightGoal = weightGoal;
	}

	public int getDailyCalories() {
		return dailyCalories;
	}

	public void setDailyCalories(int dailyCalories) {
		this.dailyCalories = dailyCalories;
	}

}
