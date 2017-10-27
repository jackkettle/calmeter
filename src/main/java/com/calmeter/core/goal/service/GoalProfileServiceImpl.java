package com.calmeter.core.goal.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.calmeter.core.account.model.User;
import com.calmeter.core.goal.model.GoalProfile;
import com.calmeter.core.goal.repository.IGoalProfileRepository;

@Component("goalProfileService")
@Transactional
public class GoalProfileServiceImpl implements IGoalProfileService {

	@Autowired
	IGoalProfileRepository goalProfileRepository;

	@Override
	public Optional<GoalProfile> get(User user) {
		return goalProfileRepository.findByUser(user);
	}

	@Override
	public Optional<GoalProfile> get(Long id) {
		return goalProfileRepository.findById(id);
	}
	
	@Override
	public GoalProfile save(GoalProfile goalProfile) {
		return goalProfileRepository.save(goalProfile);
	}

}
