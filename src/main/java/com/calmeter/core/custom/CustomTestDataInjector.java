package com.calmeter.core.custom;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.calmeter.core.account.model.Role;
import com.calmeter.core.account.model.User;
import com.calmeter.core.account.model.UserRole;
import com.calmeter.core.account.repository.IUserRepository;
import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.nutrient.NutritionalInformation;
import com.calmeter.core.food.model.nutrient.micro.MineralLabel;
import com.calmeter.core.food.repositroy.IFoodItemRepository;

@Component
public class CustomTestDataInjector {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IFoodItemRepository foodItemRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public void fillWithTestData () {
		userRepository.deleteAll ();
		Optional<User> userWrapper = userRepository.findByUsername ("john.doe");

		User user = null;
		if (!userWrapper.isPresent ()) {
			user = new User ();
			user.setEmail ("john.doe@example.com");
			user.setPassword (encoder.encode ("password"));
			user.setUsername ("john.doe");
			UserRole role = new UserRole();
			role.setRole (Role.MEMBER);
			user.getRoles ().add (role);
			userRepository.save (user);
		}
		else {
			user = userWrapper.get ();
		}

		foodItemRepository.deleteAll ();

		NutritionalInformation nutritionalInformation = new NutritionalInformation ();
		nutritionalInformation.setServingSize (118.0);
		nutritionalInformation.setCalories (105.0);
		nutritionalInformation.getConsolidatedCarbs ().setSugar (14.0);
		nutritionalInformation.getConsolidatedCarbs ().setTotal (27.0);
		nutritionalInformation.getConsolidatedFats ().setCholesterol (0.0);
		nutritionalInformation.getConsolidatedFats ().setSaturatedFat (0.1);
		nutritionalInformation.getConsolidatedFats ().setTotalFat (0.4);

		nutritionalInformation.getConsolidatedProteins ().setProtein (1.3);
		nutritionalInformation.getMineralMap ().put (MineralLabel.SODIUM, 1.2);

		FoodItem foodItem = new FoodItem ();
		foodItem.setName ("Banana");
		foodItem.setWeightInGrams (118);
		foodItem.setNutritionalInformation (nutritionalInformation);
		foodItem.setCreator (user);

		foodItemRepository.save (foodItem);

		nutritionalInformation = new NutritionalInformation ();
		nutritionalInformation.setServingSize (46.0);
		nutritionalInformation.setCalories (90.0);
		nutritionalInformation.getConsolidatedCarbs ().setSugar (0.2);
		nutritionalInformation.getConsolidatedCarbs ().setTotal (0.4);
		nutritionalInformation.getConsolidatedFats ().setCholesterol (184.5);
		nutritionalInformation.getConsolidatedFats ().setSaturatedFat (2.0);
		nutritionalInformation.getConsolidatedFats ().setTotalFat (7.0);

		nutritionalInformation.getConsolidatedProteins ().setProtein (6.0);
		nutritionalInformation.getMineralMap ().put (MineralLabel.SODIUM, 95.2);

		foodItem = new FoodItem ();
		foodItem.setName ("Egg, fried");
		foodItem.setWeightInGrams (46);
		foodItem.setNutritionalInformation (nutritionalInformation);
		foodItem.setCreator (user);

		foodItemRepository.save (foodItem);

	}

}
