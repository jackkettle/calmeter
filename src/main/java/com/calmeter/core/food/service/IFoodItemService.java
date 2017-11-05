package com.calmeter.core.food.service;

import java.util.List;
import java.util.Optional;

import com.calmeter.core.account.model.User;
import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.FoodItemType;

public interface IFoodItemService {

	Optional<FoodItem> get(long id);

	Optional<FoodItem> get(String name);

	List<FoodItem> getAll(User user);

	FoodItem save(FoodItem foodItem);

	void delete(Long id);

	void delete(FoodItem foodItem);

	Optional<FoodItem> getExternal(long externalId, FoodItemType foodItemType);

	boolean existsExternal(long externalId, FoodItemType foodItemType);

	List<FoodItem> search(String query, boolean showDisabled, User user);

	boolean isUsed(FoodItem foodItem);

}
