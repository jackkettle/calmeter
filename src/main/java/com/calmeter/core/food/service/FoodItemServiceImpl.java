package com.calmeter.core.food.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.calmeter.core.account.model.User;
import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.FoodItemType;
import com.calmeter.core.food.repositroy.IFoodItemRepository;

@Component("foodItemService")
@Transactional
public class FoodItemServiceImpl implements IFoodItemService {

	@Autowired
	IFoodItemRepository foodItemRepository;

	@Override
	public Optional<FoodItem> get(long id) {
		return foodItemRepository.findById(id);
	}

	@Override
	public Optional<FoodItem> get(String name) {
		return foodItemRepository.findByName(name);
	}

	@Override
	public List<FoodItem> getAll(User user) {
		return foodItemRepository.findAllByCreator(user);
	}

	@Override
	public FoodItem save(FoodItem foodItem) {
		return foodItemRepository.save(foodItem);
	}

	@Override
	public List<FoodItem> search(String query) {
		return foodItemRepository.findByNameContainingIgnoreCase(query);
	}

	@Override
	public Optional<FoodItem> getExternal(long externalId, FoodItemType foodItemType) {
		return foodItemRepository.findByExternalIdAndFoodItemType(externalId, foodItemType);
	}

	@Override
	public boolean existsExternal(long externalId, FoodItemType foodItemType) {
		return foodItemRepository.existsByExternalIdAndFoodItemType(externalId, foodItemType);
	}
	
	@Override
	public void delete(Long id) {
		foodItemRepository.delete(id);
	}

	@Override
	public void delete(FoodItem foodItem) {
		foodItemRepository.delete(foodItem);
	}

}
