package com.calmeter.core.food.source.service;

import java.util.Optional;

import com.calmeter.core.food.source.model.FoodSource;

public interface IFoodSourceService {

	Optional<FoodSource> findByName (String name);

}
