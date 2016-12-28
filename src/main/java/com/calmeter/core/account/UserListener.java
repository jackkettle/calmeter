package com.calmeter.core.account;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.calmeter.core.food.VitaminLabel;
import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.NutritionalInformation;
import com.calmeter.core.food.repositroy.FoodItemRepository;
import com.calmeter.core.EventBusFactory;
import com.calmeter.core.account.model.UserEvent;
import com.google.common.eventbus.Subscribe;

@Component
public class UserListener {

	@Autowired
	EventBusFactory eventBusFactory;

	@Autowired
	private FoodItemRepository foodItemRepository;

	@PostConstruct
	public void init() {
		logger.info("UserListener init");
		eventBusFactory.getInstance().register(this);

	}

	@Subscribe
	public void handleUserEvent(UserEvent e) {
		logger.info("Record event");

		NutritionalInformation nutritionalInformation = new NutritionalInformation();
		nutritionalInformation.setCalories(1000);
		nutritionalInformation.setCarbohydrate(500);
		nutritionalInformation.getVitaminMap().put(VitaminLabel.VITAMIN_B12, 5000.0);
		nutritionalInformation.getVitaminMap().put(VitaminLabel.VITAMIN_A, 30.0);		
		
		FoodItem foodItem = new FoodItem();
		foodItem.setName("Banana");
		foodItem.setWeightInGrams(1000);
		foodItem.setNutritionalInformation(nutritionalInformation);
		
		foodItemRepository.save(foodItem);
	}

	private static Logger logger = LoggerFactory.getLogger(UserListener.class);

}
