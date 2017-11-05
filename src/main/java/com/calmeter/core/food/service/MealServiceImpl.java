package com.calmeter.core.food.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.calmeter.core.account.model.User;
import com.calmeter.core.food.model.Meal;
import com.calmeter.core.food.repositroy.IMealRepository;

@Component
@Transactional
public class MealServiceImpl implements IMealService {

	@Autowired
	IMealRepository mealRepository;
	
	@Autowired
	IFoodItemEntryService foodItemEntryService;
	
	@Override
	public Optional<Meal> get(Long id){
		return mealRepository.findById(id);
	}

	@Override
	public Optional<Meal> get(String name){
		return mealRepository.findByName(name);
	}
	
	@Override
	public List<Meal> getAll(User user) {
		return mealRepository.findAllByCreator(user);
	}
	
	@Override
	public List<Meal> search(String query, boolean showDisabled, User user) {
		return mealRepository.findByNameContainingIgnoreCaseAndDisabledAndCreator(query, showDisabled, user);

	}
	
	@Override
	public void delete(Meal meal) {
		 mealRepository.delete(meal);

	}
	
	@Override
	public void delete(Long id) {
		mealRepository.delete(id);
	}
	
	@Override
	public Meal save(Meal meal) {
		return mealRepository.save(meal);

	}

	@Override
	public boolean isUsed(Meal meal) {
		return foodItemEntryService.isMealUsed(meal);
	}
	
}
