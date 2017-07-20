package com.calmeter.core.goal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calmeter.core.account.model.User;
import com.calmeter.core.goal.model.GoalProfile;

public interface IGoalProfileRepository extends JpaRepository<GoalProfile, Long> {

	Optional<GoalProfile> findByUser(User user);
	
	Optional<GoalProfile> findById(Long id);

}
