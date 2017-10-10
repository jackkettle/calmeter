package com.calmeter.core.goal.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.calmeter.core.account.model.User;
import com.calmeter.core.account.utils.UserHelper;
import com.calmeter.core.goal.model.ActivityLevel;
import com.calmeter.core.goal.model.GoalProfile;
import com.calmeter.core.goal.model.NutritionalRatio;
import com.calmeter.core.goal.model.WeightGoal;
import com.calmeter.core.goal.service.IGoalProfileService;
import com.calmeter.core.goal.service.INutritionalRatioService;
import com.calmeter.core.goal.utils.GoalProfileHelper;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

@Component
public class GoalProfileDeserializer extends JsonDeserializer<GoalProfile> {

	@Autowired
	UserHelper userHelper;
	
	@Autowired
	GoalProfileHelper goalProfileHelper;

	@Autowired
	INutritionalRatioService nutritionalRatioService;

	@Autowired
	IGoalProfileService goalProfileService;

	@Override
	public GoalProfile deserialize(JsonParser jsonParser, DeserializationContext context)
			throws IOException, JsonProcessingException {

		GoalProfile goalProfile = new GoalProfile();

		Optional<User> userWrapper = userHelper.getLoggedInUser();
		if (userWrapper.isPresent()) {
			Optional<GoalProfile> goalProfileWrapper = goalProfileService.get(userWrapper.get());
			if (goalProfileWrapper.isPresent())
				goalProfile = goalProfileWrapper.get();
		}
		JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);

		int userBMR = rootNode.get("userBMR").asInt();
		String activityLevelText = rootNode.get("activityLevel").asText();
		String weightGoalText = rootNode.get("weightGoal").asText();
		Long ratioId = rootNode.get("ratio").asLong();

		goalProfile.setUserBMR(userBMR);

		ActivityLevel activityLevel = ActivityLevel.fromString(activityLevelText);
		goalProfile.setActivityLevel(activityLevel);

		WeightGoal weightGoal = WeightGoal.fromString(weightGoalText);
		goalProfile.setWeightGoal(weightGoal);
		
		int dailyCalories = goalProfileHelper.getDailyCalories(userWrapper.get(), activityLevel);
		dailyCalories = dailyCalories + weightGoal.getCaloriesModifer();
		goalProfile.setDailyCalories(dailyCalories);
		
		Optional<NutritionalRatio> nutritionalRatioWrapper = nutritionalRatioService.getRatio(ratioId);
		if (nutritionalRatioWrapper.isPresent()) {
			goalProfile.setNutritionalRatio(nutritionalRatioWrapper.get());
		}

		return goalProfile;
	}

}
