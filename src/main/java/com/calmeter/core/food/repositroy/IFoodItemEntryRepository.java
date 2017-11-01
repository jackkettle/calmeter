package com.calmeter.core.food.repositroy;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.FoodItemEntry;

public interface IFoodItemEntryRepository extends JpaRepository<FoodItemEntry, Long> {
	
	Optional<FoodItemEntry> getById(Long id);
	
	List<FoodItemEntry> getByFoodItem(FoodItem foodItem);
	
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM FoodItemEntry f WHERE f.foodItem = :foodItem")
	boolean existsByFoodItem(@Param("foodItem") FoodItem foodItem);

}
