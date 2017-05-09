package com.calmeter.core.food.source.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calmeter.core.food.source.model.FoodSource;

public interface IFoodSourceRepository extends JpaRepository<FoodSource, Long> {

	FoodSource findByName(String name);

}
