package com.calmeter.core.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.calmeter.core.account.model.User;
import com.calmeter.core.account.repository.IUserRepository;
import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.nutrient.NutritionalInformation;
import com.calmeter.core.food.model.nutrient.micro.VitaminLabel;
import com.calmeter.core.food.repositroy.IFoodItemRepository;

@Component
public class CustomTestDataInjector {

	@Autowired
	IUserRepository userRepository;

	@Autowired
	IFoodItemRepository foodItemRepository;

	public void fillWithTestData() {
		User user = userRepository.findByUsername("john.doe");

		if (user == null) {
			user = new User();
			user.setEmail("john.doe@example.com");
			user.setPassword("password");
			user.setUsername("john.doe");

			userRepository.save(user);
		}

		if (foodItemRepository.findAll().size() > 1) {
			return;
		}

		NutritionalInformation nutritionalInformation = new NutritionalInformation();
		nutritionalInformation.setCalories(1000.0);
		nutritionalInformation.getConsolidatedCarbs().setSugar(10.0);
		nutritionalInformation.getConsolidatedFats().setCholesterol(3.0);
		nutritionalInformation.getConsolidatedProteins().setProtein(20.0);
		nutritionalInformation.getVitaminMap().put(VitaminLabel.VITAMIN_B12, 5000.0);
		nutritionalInformation.getVitaminMap().put(VitaminLabel.VITAMIN_A, 30.0);

		FoodItem foodItem = new FoodItem();
		foodItem.setName("Banana");
		foodItem.setWeightInGrams(1000);
		foodItem.setNutritionalInformation(nutritionalInformation);
		foodItem.setCreator(user);

		foodItemRepository.save(foodItem);
	}

}
