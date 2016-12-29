package com.calmeter.core.food.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calmeter.core.food.model.Meal;

public interface MealRepository extends JpaRepository<Meal, Long> {

}