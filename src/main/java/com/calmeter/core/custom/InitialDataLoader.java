package com.calmeter.core.custom;

import java.util.HashMap;
import java.util.Map;
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
import com.calmeter.core.config.model.ConfigKey;
import com.calmeter.core.config.service.IConfigOptionService;
import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.nutrient.NutritionalInfoType;
import com.calmeter.core.food.model.nutrient.NutritionalInformation;
import com.calmeter.core.food.model.nutrient.macro.carb.ConsolidatedCarbs;
import com.calmeter.core.food.model.nutrient.macro.fat.ConsolidatedFats;
import com.calmeter.core.food.model.nutrient.macro.protein.ConsolidatedProteins;
import com.calmeter.core.food.model.nutrient.micro.MineralLabel;
import com.calmeter.core.food.model.nutrient.micro.VitaminLabel;
import com.calmeter.core.food.repositroy.IFoodItemRepository;
import com.calmeter.core.food.repositroy.INutritionalInformationRepository;
import com.calmeter.core.goal.model.NutritionalRatio;
import com.calmeter.core.goal.repository.INutritionalRatioRepository;
import com.calmeter.core.account.repository.IUserRepository;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	boolean alreadySetup = false;

	@Autowired
	private IFoodItemRepository foodItemRepository;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IUserRoleRepository roleRepository;

	@Autowired
	INutritionalRatioRepository nutritionalRatioRepository;

	@Autowired
	INutritionalInformationRepository nutritionalInformationRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	IConfigOptionService configOptionService;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {

		if (alreadySetup)
			return;

		createRoleIfNotFound(Role.ADMIN);
		createRoleIfNotFound(Role.MEMBER);
		createRoleIfNotFound(Role.PREMIUM_MEMBER);

		UserRole adminRole = roleRepository.findByRole(Role.ADMIN).get();
		UserRole memberRole = roleRepository.findByRole(Role.MEMBER).get();
		UserRole premiumMemberRole = roleRepository.findByRole(Role.PREMIUM_MEMBER).get();

		User user = new User();
		user.setUsername("john.doe");
		user.setEmail("john.doe@example.com");
		user.setPassword(passwordEncoder.encode("password"));
		user.getRoles().add(adminRole);
		user.getRoles().add(memberRole);
		user.getRoles().add(premiumMemberRole);
		user.setFirstname("John");
		user.setLastname("Doe");
		user.setEnabled(true);
		userRepository.save(user);

		NutritionalInformation nutritionalInformation = new NutritionalInformation();
		nutritionalInformation.setServingSize(118.0);
		nutritionalInformation.setCalories(105.0);
		nutritionalInformation.getConsolidatedCarbs().setSugar(14.0);
		nutritionalInformation.getConsolidatedCarbs().setTotal(27.0);
		nutritionalInformation.getConsolidatedFats().setCholesterol(0.0);
		nutritionalInformation.getConsolidatedFats().setSaturatedFat(0.1);
		nutritionalInformation.getConsolidatedFats().setTotalFat(0.4);

		nutritionalInformation.getConsolidatedProteins().setProtein(1.3);
		nutritionalInformation.getMineralMap().put(MineralLabel.SODIUM, 1.2);

		FoodItem foodItem = new FoodItem();
		foodItem.setName("Banana");
		foodItem.setWeightInGrams(118.0);
		foodItem.setNutritionalInformation(nutritionalInformation);
		foodItem.setCreator(user);

		createFoodItemIfNotFound(foodItem);

		nutritionalInformation = new NutritionalInformation();
		nutritionalInformation.setServingSize(46.0);
		nutritionalInformation.setCalories(90.0);
		nutritionalInformation.getConsolidatedCarbs().setSugar(0.2);
		nutritionalInformation.getConsolidatedCarbs().setTotal(0.4);
		nutritionalInformation.getConsolidatedFats().setCholesterol(184.5);
		nutritionalInformation.getConsolidatedFats().setSaturatedFat(2.0);
		nutritionalInformation.getConsolidatedFats().setTotalFat(7.0);

		nutritionalInformation.getConsolidatedProteins().setProtein(6.0);
		nutritionalInformation.getMineralMap().put(MineralLabel.SODIUM, 95.2);

		foodItem = new FoodItem();
		foodItem.setName("Egg, fried");
		foodItem.setWeightInGrams(46.0);
		foodItem.setNutritionalInformation(nutritionalInformation);
		foodItem.setCreator(user);

		createFoodItemIfNotFound(foodItem);

		createGoalNutritionalInformation();

		createBaselineNutrionalTargets();

		alreadySetup = true;
	}

	@Transactional
	private UserRole createRoleIfNotFound(Role role) {

		Optional<UserRole> userRoleWrapper = roleRepository.findByRole(role);
		UserRole userRole = null;
		if (!userRoleWrapper.isPresent()) {
			userRole = new UserRole(role);
			roleRepository.save(userRole);
		} else {
			userRole = userRoleWrapper.get();
		}
		return userRole;
	}

	@Transactional
	private FoodItem createFoodItemIfNotFound(FoodItem foodItem) {

		Optional<FoodItem> foodItemWrapper = foodItemRepository.findByName(foodItem.getName());

		if (!foodItemWrapper.isPresent()) {
			return foodItemRepository.save(foodItem);
		}
		return foodItemWrapper.get();
	}

	@Transactional
	private void createGoalNutritionalInformation() {

		NutritionalRatio nutritionalRatio = new NutritionalRatio();
		NutritionalInformation nutritionalInformation = new NutritionalInformation(NutritionalInfoType.GOAL);
		nutritionalRatio.setGlobal(true);

		nutritionalRatio.setName("The Zone Diet");
		nutritionalRatio.setDescription(
				"<p>The Zone diet, created by Dr. Barry Sears and promoted by Crossfit, is based on the idea that the correct "
						+ "balance of macronutrients (Protein, Carbohydrate, and Fat) you eat will create a hormonal response in "
						+ "your body that can lead you to optimal health.</p>");

		nutritionalInformation.getConsolidatedCarbs().setTotal(40.0);
		nutritionalInformation.getConsolidatedProteins().setProtein(30.0);
		nutritionalInformation.getConsolidatedFats().setTotalFat(30.0);
		nutritionalRatio.setNutritionalInformation(nutritionalInformation);
		nutritionalRatioRepository.save(nutritionalRatio);

	}

	@Transactional
	private void createBaselineNutrionalTargets() {

		NutritionalInformation nutritionalInformation = new NutritionalInformation(NutritionalInfoType.BASELINE);

		nutritionalInformation.setCalories(2000.0);
		nutritionalInformation.setCaffeine(300);

		ConsolidatedCarbs consolidatedCarbs = new ConsolidatedCarbs();
		consolidatedCarbs.setFiber(28.0);
		// consolidatedCarbs.setStarch(consolidatedCarbsNode.get("starch").asDouble());
		// consolidatedCarbs.setSugarAlcohol(consolidatedCarbsNode.get("sugarAlcohol").asDouble());
		consolidatedCarbs.setSugar(50.0);
		consolidatedCarbs.setTotal(275.0);

		ConsolidatedFats consolidatedFats = new ConsolidatedFats();
		consolidatedFats.setSaturatedFat(20.0);
		// consolidatedFats.setMonoUnsaturatedFat(consolidatedFatsNode.get("monoUnsaturatedFat").asDouble());
		// consolidatedFats.setPolyUnsaturatedFat(consolidatedFatsNode.get("polyUnsaturatedFat").asDouble());
		// consolidatedFats.setTransFat(consolidatedFatsNode.get("transFat").asDouble());
		consolidatedFats.setCholesterol(300.0);
		consolidatedFats.setTotalFat(78.0);

		ConsolidatedProteins consolidatedProteins = new ConsolidatedProteins();
		consolidatedProteins.setProtein(50.0);

		Map<MineralLabel, Double> mineralMap = new HashMap<>();
		mineralMap.put(MineralLabel.SODIUM, 2300.0);
		mineralMap.put(MineralLabel.POTASSIUM, 4700.0);
		nutritionalInformation.setMineralMap(mineralMap);

		Map<VitaminLabel, Double> vitaminMap = new HashMap<>();
		nutritionalInformation.setVitaminMap(vitaminMap);

		nutritionalInformation.setConsolidatedFats(consolidatedFats);
		nutritionalInformation.setConsolidatedCarbs(consolidatedCarbs);
		nutritionalInformation.setConsolidatedProteins(consolidatedProteins);

		nutritionalInformation = nutritionalInformationRepository.save(nutritionalInformation);
		configOptionService.setConfigKey(ConfigKey.NUTRIONAL_BASELINE_ID, nutritionalInformation.getId() + "");

	}
}
