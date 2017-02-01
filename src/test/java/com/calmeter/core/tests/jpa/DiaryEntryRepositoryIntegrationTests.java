package com.calmeter.core.tests.jpa;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.calmeter.core.account.model.User;
import com.calmeter.core.account.repository.IUserRepository;
import com.calmeter.core.diary.model.DiaryEntry;
import com.calmeter.core.diary.repository.IDiaryEntryRepository;
import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.nutrient.NutritionalInformation;
import com.calmeter.core.food.model.nutrient.micro.VitaminLabel;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class DiaryEntryRepositoryIntegrationTests {
	
	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	IDiaryEntryRepository diaryEntryRepository;
	
	@Autowired
	EntityManager entityManager;

	private LocalDateTime localDateTime;
	
    @Before
    @Transactional
    public void runBeforeTestMethod() {
    	
		User user = userRepository.findByUsername(Constants.USERNAME);

		if (user == null) {
			user = new User();
			user.setEmail(Constants.EMAIL);
			user.setPassword(Constants.PASSWORD);
			user.setUsername(Constants.USERNAME);

			entityManager.persist(user);
			entityManager.flush();
		}

		NutritionalInformation nutritionalInformation = new NutritionalInformation();
		nutritionalInformation.setCalories(1000.0);
		nutritionalInformation.getConsolidatedCarbs ().setSugar (10.0);
		nutritionalInformation.getConsolidatedFats ().setCholesterol (3.0);
		nutritionalInformation.getConsolidatedProteins ().setProtein (20.0);
		nutritionalInformation.getVitaminMap().put(VitaminLabel.VITAMIN_B12, 5000.0);
		nutritionalInformation.getVitaminMap().put(VitaminLabel.VITAMIN_A, 30.0);

		FoodItem foodItem = new FoodItem();
		foodItem.setName("Banana");
		foodItem.setWeightInGrams(1000);
		foodItem.setNutritionalInformation(nutritionalInformation);
		foodItem.setCreator(user);

		entityManager.persist(foodItem);
		entityManager.flush();
    	
    	localDateTime = LocalDateTime.now();

    	List<FoodItem> foodItems = new ArrayList<FoodItem>();
    	foodItems.add(foodItem);
    	
    	DiaryEntry diaryEntry = new DiaryEntry();
    	diaryEntry.setUser(user);
    	diaryEntry.setFoodItems(foodItems);
    	diaryEntry.setTime(localDateTime);
    	
    	entityManager.persist(diaryEntry);
    	
    }
	
	@Test
	@Transactional
	public void findByUserNameTest() throws Exception {
		DiaryEntry diaryEntry = diaryEntryRepository.findAll().get(0);
		logger.info("{}", diaryEntry.getTime());

	}

	private static Logger logger = LoggerFactory.getLogger(DiaryEntryRepositoryIntegrationTests.class);
	
}
