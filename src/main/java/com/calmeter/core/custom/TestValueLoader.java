package com.calmeter.core.custom;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import com.calmeter.core.account.model.*;
import com.calmeter.core.goal.model.GoalProfile;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.calmeter.core.account.repository.IUserRepository;
import com.calmeter.core.account.repository.IUserRoleRepository;
import com.calmeter.core.config.model.ConfigKey;
import com.calmeter.core.config.service.IConfigOptionService;
import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.FoodItemType;
import com.calmeter.core.food.model.nutrient.NutritionalInfoType;
import com.calmeter.core.food.model.nutrient.NutritionalInformation;
import com.calmeter.core.food.model.nutrient.macro.carb.ConsolidatedCarbs;
import com.calmeter.core.food.model.nutrient.macro.fat.ConsolidatedFats;
import com.calmeter.core.food.model.nutrient.macro.protein.ConsolidatedProteins;
import com.calmeter.core.food.model.nutrient.micro.MineralLabel;
import com.calmeter.core.food.model.nutrient.micro.VitaminLabel;
import com.calmeter.core.food.repositroy.IFoodItemRepository;
import com.calmeter.core.food.repositroy.INutritionalInformationRepository;
import com.calmeter.core.food.source.handler.FoodHandler;
import com.calmeter.core.food.source.handler.IFoodSourceHandler;
import com.calmeter.core.food.source.handler.RecipesHandler;
import com.calmeter.core.food.source.handler.TescoHandler;
import com.calmeter.core.food.source.model.FoodSource;
import com.calmeter.core.food.source.repository.IFoodSourceRepository;
import com.calmeter.core.goal.model.NutritionalRatio;
import com.calmeter.core.goal.repository.INutritionalRatioRepository;

@Component
public class TestValueLoader {

    @Autowired
    private IFoodItemRepository foodItemRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IUserRoleRepository roleRepository;

    @Autowired
    INutritionalRatioRepository nutritionalRatioRepository;

    @Autowired
    IFoodSourceRepository foodSourceRepository;

    @Autowired
    INutritionalInformationRepository nutritionalInformationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    IConfigOptionService configOptionService;

    public void loadValues() {
        createRoleIfNotFound(Role.ADMIN);
        createRoleIfNotFound(Role.MEMBER);
        createRoleIfNotFound(Role.PREMIUM_MEMBER);

        UserRole adminRole = roleRepository.findByRole(Role.ADMIN).get();
        UserRole memberRole = roleRepository.findByRole(Role.MEMBER).get();
        UserRole premiumMemberRole = roleRepository.findByRole(Role.PREMIUM_MEMBER).get();

        List<UserRole> roles = Arrays.asList(adminRole, memberRole, premiumMemberRole);

        User user = createAdminUserIfNotFound(roles);

        createFoodItems(user);

        createGoalNutritionalInformation();

        createBaselineNutrionalTargets();

        loadFoodSources();

    }

    public void createFoodItems(User user) {
        NutritionalInformation nutritionalInformation = new NutritionalInformation();
        nutritionalInformation.setServingSize(118.0);
        nutritionalInformation.setCalories(89.0);
        nutritionalInformation.getConsolidatedCarbs().setSugar(12.0);
        nutritionalInformation.getConsolidatedCarbs().setTotal(23.0);
        nutritionalInformation.getConsolidatedFats().setCholesterol(0.0);
        nutritionalInformation.getConsolidatedFats().setSaturatedFat(0.1);
        nutritionalInformation.getConsolidatedFats().setTotalFat(0.3);

        nutritionalInformation.getConsolidatedProteins().setProtein(1.1);
        nutritionalInformation.getMineralMap().put(MineralLabel.SODIUM, 1.0);

        FoodItem foodItem = new FoodItem(FoodItemType.USER_ITEM);
        foodItem.setName("Banana");
        foodItem.setNutritionalInformation(nutritionalInformation);
        foodItem.setCreator(user);
        foodItem.setDisabled(false);

        createFoodItemIfNotFound(foodItem);

        nutritionalInformation = new NutritionalInformation();
        nutritionalInformation.setServingSize(46.0);
        nutritionalInformation.setCalories(195.0);
        nutritionalInformation.getConsolidatedCarbs().setSugar(0.4);
        nutritionalInformation.getConsolidatedCarbs().setTotal(0.8);
        nutritionalInformation.getConsolidatedFats().setCholesterol(401.0);
        nutritionalInformation.getConsolidatedFats().setSaturatedFat(4.3);
        nutritionalInformation.getConsolidatedFats().setTotalFat(15.0);

        nutritionalInformation.getConsolidatedProteins().setProtein(14.0);
        nutritionalInformation.getMineralMap().put(MineralLabel.SODIUM, 207.0);

        foodItem = new FoodItem();
        foodItem.setName("Egg, fried");
        foodItem.setNutritionalInformation(nutritionalInformation);
        foodItem.setCreator(user);
        foodItem.setDisabled(false);

        createFoodItemIfNotFound(foodItem);
    }

    private User createAdminUserIfNotFound(List<UserRole> roles) {

        Optional<User> userWrapper = userRepository.findByUsername("john.doe");

        if (userWrapper.isPresent()) {
            return userWrapper.get();
        }

        User user = new User();
        user.setUsername("john.doe");
        user.setEmail("john.doe@example.com");
        user.setPassword(passwordEncoder.encode("password"));
        user.getRoles().addAll(roles);
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setEnabled(true);
        user.setIsUserProfileSet(true);
        user.setUserProfile(createUserProfile());

        return userRepository.save(user);
    }

    private UserProfile createUserProfile() {

        UserProfile userProfile = new UserProfile();
        userProfile.setSex(Sex.MALE);
        userProfile.setHeight(170.0);
        userProfile.setDateOfBirth(LocalDate.parse("1995-10-10"));

        WeightLogEntry weightLogEntry1 = new WeightLogEntry();
        weightLogEntry1.setWeightInKgs(75.0);
        weightLogEntry1.setDateTime(LocalDateTime.parse("2017-12-03T10:15:30"));

        WeightLogEntry weightLogEntry2 = new WeightLogEntry();
        weightLogEntry2.setWeightInKgs(85.0);
        weightLogEntry2.setDateTime(LocalDateTime.parse("2017-12-03T10:20:30"));

        WeightLogEntry weightLogEntry3 = new WeightLogEntry();
        weightLogEntry3.setWeightInKgs(80.0);
        weightLogEntry3.setDateTime(LocalDateTime.parse("2017-12-03T10:18:30"));

        userProfile.getWeightLog().add(weightLogEntry1);
        userProfile.getWeightLog().add(weightLogEntry2);
        userProfile.getWeightLog().add(weightLogEntry3);

        return userProfile;
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

        if (nutritionalRatioRepository.findByName("The Zone Diet").isPresent()) {
            return;
        }

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

        if (configOptionService.findByConfigKey(ConfigKey.NUTRIONAL_BASELINE_ID).isPresent()) {
            return;
        }

        NutritionalInformation nutritionalInformation = new NutritionalInformation(NutritionalInfoType.BASELINE);

        nutritionalInformation.setCalories(2000.0);
        nutritionalInformation.setCaffeine(300);

        ConsolidatedCarbs consolidatedCarbs = new ConsolidatedCarbs();
        consolidatedCarbs.setFiber(28.0);
        consolidatedCarbs.setSugar(50.0);
        consolidatedCarbs.setTotal(275.0);

        ConsolidatedFats consolidatedFats = new ConsolidatedFats();
        consolidatedFats.setSaturatedFat(20.0);
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

    @SuppressWarnings("unchecked")
    @Transactional
    public void loadFoodSources() {

        if (!foodSourceRepository.findByName("food").isPresent()) {

            IFoodSourceHandler foodHandler = new FoodHandler();
            FoodSource foodSource = new FoodSource();
            foodSource.setName("food");
            foodSource.setSourceHandler((Class<IFoodSourceHandler>) foodHandler.getClass());

            foodSourceRepository.save(foodSource);
        }
        if (!foodSourceRepository.findByName("recipes").isPresent()) {

            IFoodSourceHandler foodHandler = new RecipesHandler();
            FoodSource foodSource = new FoodSource();
            foodSource.setName("recipes");
            foodSource.setSourceHandler((Class<IFoodSourceHandler>) foodHandler.getClass());

            foodSourceRepository.save(foodSource);
        }

        if (!foodSourceRepository.findByName("tesco").isPresent()) {

            IFoodSourceHandler foodHandler = new TescoHandler();
            FoodSource foodSource = new FoodSource();
            foodSource.setName("tesco");
            foodSource.setSourceHandler((Class<IFoodSourceHandler>) foodHandler.getClass());

            foodSourceRepository.save(foodSource);
        }
    }

}
