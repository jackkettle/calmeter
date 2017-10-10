package com.calmeter.core.goal.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum WeightGoal {

	LOSE_2("I want to lose 1kg (2 lb) per week", -1000), 
	LOSE_1("I want to lose .5kg (1 lb) per week", -500), 
	MAINTAIN("I want to mantain my weight", 0), 
	GAIN_1("I want to gain .5kg (1 lb) per week", 500), 
	GAIN_2("I want to gain 1kg (2 lb) per week", 1000);

	private final String text;
	
	private final int caloriesModifer;

	private WeightGoal(final String text, final int caloriesModifer) {
		this.text = text;
		this.caloriesModifer =caloriesModifer;
	}

    @JsonValue
	@Override
	public String toString() {
		return text;
	}
    
	public int getCaloriesModifer() {
		return caloriesModifer;
	}

	public static WeightGoal fromString(String text) {
		for (WeightGoal weightGoal : WeightGoal.values()) {
			if (weightGoal.text.equalsIgnoreCase(text)) {
				return weightGoal;
			}
		}
		throw new IllegalArgumentException("No constant with text " + text + " found");
	}

}
