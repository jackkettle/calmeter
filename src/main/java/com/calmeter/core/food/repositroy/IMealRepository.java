package com.calmeter.core.food.repositroy;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calmeter.core.account.model.User;
import com.calmeter.core.food.model.Meal;

public interface IMealRepository extends JpaRepository<Meal, Long> {

	Optional<Meal> findById(Long id);
	
	Optional<Meal> findByName(String name);

	List<Meal> findAllByCreator(User user);
	
	List<Meal> findByNameContainingIgnoreCaseAndDisabled (String searchString, boolean showDisabled);
	
	List<Meal> findByNameContainingIgnoreCaseAndDisabledAndCreator (String searchString, boolean showDisabled, User user);


}