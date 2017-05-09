package com.calmeter.core.food.source.utils;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.calmeter.core.food.source.model.FoodSource;
import com.calmeter.core.food.source.repository.IFoodSourceRepository;

@Component
public class FoodSourceHelper {

	@Autowired
	IFoodSourceRepository foodSourceRepository;

	public Optional<FoodSource> getFoodSourceFromName (String name) {

		FoodSource foodSource = foodSourceRepository.findByName (name);

		if (foodSource == null)
			return Optional.empty ();

		return Optional.of (foodSource);
	}

}
