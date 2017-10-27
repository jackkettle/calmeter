package com.calmeter.core.goal.service;

import java.util.Optional;

import com.calmeter.core.account.model.User;
import com.calmeter.core.goal.model.GoalProfile;

public interface IGoalProfileService {
	
	Optional<GoalProfile> get(User user);

	Optional<GoalProfile> get(Long id);

	GoalProfile save(GoalProfile goalProfile);

}
