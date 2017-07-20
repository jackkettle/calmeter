package com.calmeter.core.goal.utils;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.calmeter.core.account.model.Sex;
import com.calmeter.core.account.model.User;
import com.calmeter.core.food.model.nutrient.NutritionalInformation;
import com.calmeter.core.food.service.INutritionalInformationService;
import com.calmeter.core.goal.model.ActivityLevel;
import com.calmeter.core.goal.model.GoalProfile;
import com.calmeter.core.goal.service.IGoalProfileService;

@Component
public class GoalProfileHelper {

	@Autowired
	IGoalProfileService goalProfileService;

	@Autowired
	INutritionalInformationService nutritionalInformationService;
	
	public int getUserBMR(User user) {

		if (!user.getIsUserProfileSet()) {
			return 0;
		}

		Double weight = user.getUserProfile().getWeight();
		Double height = user.getUserProfile().getHeight();
		int age = Period.between(user.getUserProfile().getDateOfBirth(), LocalDate.now()).getYears();

		if (user.getUserProfile().getSex().equals(Sex.MALE)) {
			return (int) Math.round((10 * weight) + (6.25 * height) - (5 * age) + 5);
		} else {
			return (int) Math.round((10 * weight) + (6.25 * height) - (5 * age) - 161);
		}

	}

	public int getDailyCalories(User user) {

		if (!user.getIsUserProfileSet()) {
			return 0;
		}

		Optional<GoalProfile> goalProfileWrapper = goalProfileService.get(user);
		if (!goalProfileWrapper.isPresent()) {
			return 0;
		}

		return (int) Math
				.round(this.getUserBMR(user) * goalProfileWrapper.get().getActivityLevel().getActivityFactor());
	}

	public int getDailyCalories(User user, ActivityLevel activityLevel) {

		if (!user.getIsUserProfileSet()) {
			return 0;
		}

		return (int) Math.round(this.getUserBMR(user) * activityLevel.getActivityFactor());
	}

	public NutritionalInformation getModifiedNutritionalInfo(GoalProfile goalProfile) {
		NutritionalInformation baselineNutritionalInformation = nutritionalInformationService.getBaseline();
		return baselineNutritionalInformation;
	}

}
