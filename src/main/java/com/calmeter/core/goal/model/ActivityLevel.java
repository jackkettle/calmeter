package com.calmeter.core.goal.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ActivityLevel {

	SEDENTARY("Sedentary", 1.2), 
	LIGHTLY_ACTIVE("Lightly active", 1.375), 
	MODERATELY_ACTIVE("Moderately active", 1.55), 
	VERY_ACTIVE("Very active", 1.725), 
	EXTREMELY_ACTIVE("Extremely active", 1.9);

	private final String text;
	
	private final Double activityFactor;

	private ActivityLevel(final String text, Double activityFactor) {
		this.text = text;
		this.activityFactor = activityFactor;
	}

    @JsonValue
	@Override
	public String toString() {
		return this.text;
	}
    
	public Double getActivityFactor() {
		return this.activityFactor;
	}

	public static ActivityLevel fromString(String text) {
		for (ActivityLevel activityLevel : ActivityLevel.values()) {
			if (activityLevel.text.equalsIgnoreCase(text)) {
				return activityLevel;
			}
		}
		throw new IllegalArgumentException("No constant with text " + text + " found");
	}

}
