package com.calmeter.core.food.repositroy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calmeter.core.account.model.User;
import com.calmeter.core.food.model.Meal;

public interface IMealRepository extends JpaRepository<Meal, Long> {

	Meal findByName(String name);

	List<Meal> findAllByCreator(User user);

}