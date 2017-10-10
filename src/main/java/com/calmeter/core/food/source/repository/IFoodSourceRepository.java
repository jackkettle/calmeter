package com.calmeter.core.food.source.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calmeter.core.food.source.model.FoodSource;

public interface IFoodSourceRepository extends JpaRepository<FoodSource, Long> {

	Optional<FoodSource> findByName(String name);

}
