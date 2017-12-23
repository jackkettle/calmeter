package com.calmeter.core.food.source.handler;

import com.calmeter.core.account.model.User;
import com.calmeter.core.food.model.FoodItem;

import java.util.List;
import java.util.Optional;

public interface IExternalFoodSourceHandler {

	public Optional<FoodItem> getItemFromGtin (Long id);

}
