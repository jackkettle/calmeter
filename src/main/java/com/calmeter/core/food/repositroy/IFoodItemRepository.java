package com.calmeter.core.food.repositroy;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.calmeter.core.account.model.User;
import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.FoodItemType;

public interface IFoodItemRepository
		extends JpaRepository<FoodItem, Long> {
	
	Optional<FoodItem> findById(Long id);

	Optional<FoodItem> findByName (String name);

	List<FoodItem> findAllByCreator (User creator);

	List<FoodItem> findByNameContainingIgnoreCase (String searchString);

	Optional<FoodItem> findByExternalIdAndFoodItemType (long externalId, FoodItemType foodItemType);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM FoodItem f WHERE f.externalId = :externalId AND f.foodItemType = :foodItemType")
	boolean existsByExternalIdAndFoodItemType ( @Param("externalId") long externalId, @Param("foodItemType") FoodItemType foodItemType);

}
