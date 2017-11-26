package com.calmeter.core.food.repositroy;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.FoodItemEntry;
import com.calmeter.core.food.model.Meal;

public interface IFoodItemEntryRepository extends JpaRepository<FoodItemEntry, Long> {

	Optional<FoodItemEntry> getById(Long id);

	List<FoodItemEntry> getByFoodItemReference(FoodItem foodItem);
	
	List<Meal> getByMealReference(Meal foodItem);
	
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM FoodItemEntry f WHERE f.foodItemReference = :foodItemReference")
	boolean existsByFoodItemReference(@Param("foodItemReference") FoodItem foodItemReference);

}
