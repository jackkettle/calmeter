package com.calmeter.core.food.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calmeter.core.food.model.FoodItem;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {

}

