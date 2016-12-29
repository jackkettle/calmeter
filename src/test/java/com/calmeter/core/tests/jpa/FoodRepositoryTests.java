package com.calmeter.core.tests.jpa;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.calmeter.core.account.model.User;
import com.calmeter.core.account.repository.UserRepository;
import com.calmeter.core.food.VitaminLabel;
import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.NutritionalInformation;
import com.calmeter.core.food.repositroy.FoodItemRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class FoodRepositoryTests {

	@Autowired
	FoodItemRepository foodItemRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EntityManager entityManager;

	private static String EMAIL = "info@example.com";
	
	private static String USERNAME = "I.AM.A.TEST";

	private static String PASSWORD = "password";
	
    @Before
    @Transactional
    public void runBeforeTestMethod() {
       
    	User user = new User();
    	user.setEmail(EMAIL);
    	user.setPassword(PASSWORD);
    	user.setUsername(USERNAME);
    	
    	userRepository.save(user);
    	user = userRepository.findByUsername(USERNAME);
    	
    	NutritionalInformation nutritionalInformation = new NutritionalInformation();
		nutritionalInformation.setCalories(1000);
		nutritionalInformation.setCarbohydrate(500);
		nutritionalInformation.getVitaminMap().put(VitaminLabel.VITAMIN_B12, 5000.0);
		nutritionalInformation.getVitaminMap().put(VitaminLabel.VITAMIN_A, 30.0);

		FoodItem foodItem = new FoodItem();
		foodItem.setName("Banana");
		foodItem.setWeightInGrams(1000);
		foodItem.setNutritionalInformation(nutritionalInformation);
		foodItem.setCreator(user);

		foodItemRepository.save(foodItem);
    	
    }
	
	@Test
	@Transactional
	public void foodItemTest() throws Exception {

		FoodItem foundFoodItem = foodItemRepository.findByName("Banana");

		assertEquals("Banana", foundFoodItem.getName());
		assertEquals(1000, foundFoodItem.getWeightInGrams());
		assertEquals(1000, foundFoodItem.getNutritionalInformation().getCalories(), 0.1);
		assertEquals(500, foundFoodItem.getNutritionalInformation().getCarbohydrate(), 0.1);
		assertEquals(USERNAME, foundFoodItem.getCreator().getUsername());
		
		Double a = foundFoodItem.getNutritionalInformation().getVitaminMap().get(VitaminLabel.VITAMIN_B12);
		Double b = foundFoodItem.getNutritionalInformation().getVitaminMap().get(VitaminLabel.VITAMIN_A);
		
		if(a == null || b == null)
			throw new Exception();
		
		assertEquals(5000, a, 0.1);
		assertEquals(30.0, b, 0.1);

	}

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(FoodRepositoryTests.class);

}
