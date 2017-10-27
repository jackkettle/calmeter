package com.calmeter.core.food.repositroy;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calmeter.core.food.model.FoodItemEntry;

public interface IFoodItemEntryRepository extends JpaRepository<FoodItemEntry, Long> {
	
	Optional<FoodItemEntry> getById(Long id);

}
