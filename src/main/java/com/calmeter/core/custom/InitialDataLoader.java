package com.calmeter.core.custom;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.calmeter.core.account.model.Role;
import com.calmeter.core.account.model.User;
import com.calmeter.core.account.model.UserRole;
import com.calmeter.core.account.repository.IUserRoleRepository;
import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.nutrient.NutritionalInformation;
import com.calmeter.core.food.model.nutrient.micro.MineralLabel;
import com.calmeter.core.food.repositroy.IFoodItemRepository;
import com.calmeter.core.account.repository.IUserRepository;

@Component
public class InitialDataLoader
		implements ApplicationListener<ContextRefreshedEvent> {

	boolean alreadySetup = false;

	@Autowired
	private IFoodItemRepository foodItemRepository;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IUserRoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void onApplicationEvent (ContextRefreshedEvent event) {

		if (alreadySetup)
			return;

		createRoleIfNotFound (Role.ADMIN);
		createRoleIfNotFound (Role.MEMBER);
		createRoleIfNotFound (Role.PREMIUM_MEMBER);

		UserRole adminRole = roleRepository.findByRole (Role.ADMIN).get ();
		UserRole memberRole = roleRepository.findByRole (Role.MEMBER).get ();
		UserRole premiumMemberRole = roleRepository.findByRole (Role.PREMIUM_MEMBER).get ();

		User user = new User ();
		user.setUsername ("john.doe");
		user.setEmail ("john.doe@example.com");
		user.setPassword (passwordEncoder.encode ("password"));
		user.getRoles ().add (adminRole);
		user.getRoles ().add (memberRole);
		user.getRoles ().add (premiumMemberRole);
		user.setEnabled (true);
		userRepository.save (user);

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
		foodItem.setWeightInGrams (118.0);
		foodItem.setNutritionalInformation (nutritionalInformation);
		foodItem.setCreator (user);
		
		createFoodItemIfNotFound(foodItem);

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
		foodItem.setWeightInGrams (46.0);
		foodItem.setNutritionalInformation (nutritionalInformation);
		foodItem.setCreator (user);

		createFoodItemIfNotFound(foodItem);
		
		alreadySetup = true;
	}

	@Transactional
	private UserRole createRoleIfNotFound (Role role) {

		Optional<UserRole> userRoleWrapper = roleRepository.findByRole (role);
		UserRole userRole = null;
		if (!userRoleWrapper.isPresent ()) {
			userRole = new UserRole (role);
			roleRepository.save (userRole);
		}
		else {
			userRole = userRoleWrapper.get ();
		}
		return userRole;
	}

	@Transactional
	private FoodItem createFoodItemIfNotFound (FoodItem foodItem) {

		Optional<FoodItem> foodItemWrapper = foodItemRepository.findByName (foodItem.getName ());

		if (!foodItemWrapper.isPresent ()) {
			return foodItemRepository.save (foodItem);
		}
		return foodItemWrapper.get ();
	}
}
