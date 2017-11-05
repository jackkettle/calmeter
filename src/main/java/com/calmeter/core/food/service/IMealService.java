package com.calmeter.core.food.service;

import java.util.List;
import java.util.Optional;

import com.calmeter.core.account.model.User;
import com.calmeter.core.food.model.Meal;

public interface IMealService {

	Optional<Meal> get(Long id);

	Optional<Meal> get(String name);

	List<Meal> getAll(User user);

	List<Meal> search(String query, boolean showDisabled, User user);

	void delete(Meal meal);

	void delete(Long id);

	Meal save(Meal meal);

	boolean isUsed(Meal foodItem);

}
