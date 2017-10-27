package com.calmeter.core.goal.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.calmeter.core.account.model.User;
import com.calmeter.core.account.utils.UserHelper;
import com.calmeter.core.config.service.IConfigOptionService;
import com.calmeter.core.food.service.INutritionalInformationService;
import com.calmeter.core.goal.model.ActivityLevel;
import com.calmeter.core.goal.model.GoalProfile;
import com.calmeter.core.goal.model.NutritionalRatio;
import com.calmeter.core.goal.model.WeightGoal;
import com.calmeter.core.goal.service.IGoalProfileService;
import com.calmeter.core.goal.service.INutritionalRatioService;
import com.calmeter.core.goal.utils.GoalProfileHelper;

@RestController
@RequestMapping("/api/goals")
public class GoalProfileController {

	@Autowired
	UserHelper userHelper;

	@Autowired
	GoalProfileHelper goalProfileHelper;

	@Autowired
	IConfigOptionService configService;

	@Autowired
	IGoalProfileService goalProfileService;

	@Autowired
	INutritionalRatioService nutritionalRationService;

	@Autowired
	INutritionalInformationService nutritionalInformationService;

	@RequestMapping(value = "/getUserBMR", method = RequestMethod.GET)
	public ResponseEntity<Integer> getUserBMR() {

		Optional<User> userWrapper = userHelper.getLoggedInUser();
		if (!userWrapper.isPresent()) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		int bmr = goalProfileHelper.getUserBMR(userWrapper.get());
		return new ResponseEntity<Integer>(bmr, HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/getActivityLevels", method = RequestMethod.GET)
	public ResponseEntity<List<String>> getActivityLevels() {

		List<String> activities = Arrays.stream(ActivityLevel.values()).map(ActivityLevel::toString)
				.collect(Collectors.toList());
		;
		return new ResponseEntity<List<String>>(activities, HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/getWeightGoals", method = RequestMethod.GET)
	public ResponseEntity<List<String>> getWeightGoals() {

		List<String> weightGoals = Arrays.stream(WeightGoal.values()).map(WeightGoal::toString)
				.collect(Collectors.toList());
		;
		return new ResponseEntity<List<String>>(weightGoals, HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/getRatios", method = RequestMethod.GET)
	public ResponseEntity<List<NutritionalRatio>> getRatios() {

		Optional<User> userWrapper = userHelper.getLoggedInUser();
		if (!userWrapper.isPresent()) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		List<NutritionalRatio> rations = new ArrayList<>();
		rations.addAll(nutritionalRationService.getGlobalRatios());

		rations.addAll(nutritionalRationService.getUserRatios(userWrapper.get()));
		return new ResponseEntity<>(rations, HttpStatus.OK);
	}

	@RequestMapping(value = "/getGoalProfile", method = RequestMethod.GET)
	public ResponseEntity<GoalProfile> getGoalProfile() {

		Optional<User> userWrapper = userHelper.getLoggedInUser();
		if (!userWrapper.isPresent()) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		Optional<GoalProfile> goalProfileWrapper = goalProfileService.get(userWrapper.get());
		if (!goalProfileWrapper.isPresent()) {
			logger.info("Unalbe to get GoalProfile");

			GoalProfile goalProfile = new GoalProfile();
			goalProfile.setNutritionalInformation(nutritionalInformationService.getBaseline());
			return new ResponseEntity<>(goalProfile, HttpStatus.OK);
		}

		return new ResponseEntity<>(goalProfileWrapper.get(), HttpStatus.OK);
	}

	@RequestMapping(value = "/setGoalProfile", method = RequestMethod.POST)
	public ResponseEntity<GoalProfile> setGoalProfile(@RequestBody GoalProfile goalProfile) {

		Optional<User> userWrapper = userHelper.getLoggedInUser();
		if (!userWrapper.isPresent()) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		goalProfile.setUser(userWrapper.get());
		goalProfile.setNutritionalInformation(goalProfileHelper.getModifiedNutritionalInfo(goalProfile));
		
		return new ResponseEntity<>(goalProfileService.save(goalProfile), HttpStatus.OK);

	}

	public static final Logger logger = LoggerFactory.getLogger(GoalProfileController.class);

}
