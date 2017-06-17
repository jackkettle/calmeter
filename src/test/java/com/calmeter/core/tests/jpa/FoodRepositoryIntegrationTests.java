package com.calmeter.core.tests.jpa;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;

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
import com.calmeter.core.account.repository.IUserRepository;
import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.nutrient.NutritionalInformation;
import com.calmeter.core.food.model.nutrient.micro.VitaminLabel;
import com.calmeter.core.food.repositroy.IFoodItemRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class FoodRepositoryIntegrationTests {

	@Autowired
	IFoodItemRepository foodItemRepository;

	@Autowired
	IUserRepository userRepository;

	@Autowired
	EntityManager entityManager;

	@Before
	@Transactional
	public void runBeforeTestMethod () {

		Optional<User> userWrapper = userRepository.findByUsername (Constants.USERNAME);
		User user = null;
		
		if (!userWrapper.isPresent ()) {
			user = new User ();
			user.setEmail (Constants.EMAIL);
			user.setPassword (Constants.PASSWORD);
			user.setUsername (Constants.USERNAME);

			entityManager.persist (user);
			entityManager.flush ();
		}
		else {
			user = userWrapper.get ();
		}

		NutritionalInformation nutritionalInformation = new NutritionalInformation ();
		nutritionalInformation.setCalories (1000.0);
		nutritionalInformation.getConsolidatedCarbs ().setSugar (10.0);
		nutritionalInformation.getConsolidatedFats ().setCholesterol (3.0);
		nutritionalInformation.getConsolidatedProteins ().setProtein (20.0);
		nutritionalInformation.getVitaminMap ().put (VitaminLabel.VITAMIN_B12, 5000.0);
		nutritionalInformation.getVitaminMap ().put (VitaminLabel.VITAMIN_A, 30.0);

		FoodItem foodItem = new FoodItem ();
		foodItem.setName ("Banana");
		foodItem.setWeightInGrams (1000);
		foodItem.setNutritionalInformation (nutritionalInformation);
		foodItem.setCreator (user);

		entityManager.persist (foodItem);
		entityManager.flush ();

	}

	@Test
	@Transactional
	public void foodItemTest ()
			throws Exception {

		Optional<FoodItem> foundFoodItemWrapper = foodItemRepository.findByName ("Banana");

		if (!foundFoodItemWrapper.isPresent ()) {
			throw new Exception ("Could not find foodItem");
		}

		FoodItem foundFoodItem = foundFoodItemWrapper.get ();
		assertEquals ("Banana", foundFoodItem.getName ());
		assertEquals (1000, foundFoodItem.getWeightInGrams ());
		assertEquals (1000, foundFoodItem.getNutritionalInformation ().getCalories (), 0.1);
		assertEquals (10.0, foundFoodItem.getNutritionalInformation ().getConsolidatedCarbs ().getSugar (), 0.1);
		assertEquals (Constants.USERNAME, foundFoodItem.getCreator ().getUsername ());

		Double a = foundFoodItem.getNutritionalInformation ().getVitaminMap ().get (VitaminLabel.VITAMIN_B12);
		Double b = foundFoodItem.getNutritionalInformation ().getVitaminMap ().get (VitaminLabel.VITAMIN_A);

		if (a == null || b == null)
			throw new Exception ();

		assertEquals (5000, a, 0.1);
		assertEquals (30.0, b, 0.1);

	}

	@Test
	@Transactional
	public void foodItemsByUserTest ()
			throws Exception {

		Optional<User> userWrapper = userRepository.findByUsername (Constants.USERNAME);
		if (!userWrapper.isPresent ()) {
			throw new Exception ("Could not find user");
		}
		User user = userWrapper.get ();

		NutritionalInformation nutritionalInformation = new NutritionalInformation ();
		nutritionalInformation.setCalories (1000.0);
		nutritionalInformation.getConsolidatedCarbs ().setSugar (10.0);
		nutritionalInformation.getConsolidatedFats ().setCholesterol (3.0);
		nutritionalInformation.getConsolidatedProteins ().setProtein (20.0);
		nutritionalInformation.getVitaminMap ().put (VitaminLabel.VITAMIN_B12, 5000.0);
		nutritionalInformation.getVitaminMap ().put (VitaminLabel.VITAMIN_A, 30.0);

		FoodItem foodItem = new FoodItem ();
		foodItem.setName ("Apples");
		foodItem.setWeightInGrams (1000);
		foodItem.setNutritionalInformation (nutritionalInformation);
		foodItem.setCreator (user);

		foodItemRepository.save (foodItem);

		List<FoodItem> userItems = foodItemRepository.findAllByCreator (user);

		assertEquals (2, userItems.size ());

	}

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger (FoodRepositoryIntegrationTests.class);

}
