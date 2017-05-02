package com.calmeter.core.food.repositroy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calmeter.core.account.model.User;
import com.calmeter.core.food.model.FoodItem;

public interface IFoodItemRepository extends JpaRepository<FoodItem, Long> {
	FoodItem findByName(String name);
	
	List<FoodItem> findAllByCreator(User creator);
}
