package com.calmeter.core.tests.diary.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.calmeter.core.diary.model.DiaryEntry;
import com.calmeter.core.diary.service.IDiaryEntryService;
import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.service.IFoodItemService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DiaryEntryHelperTests {

	@Autowired
	IFoodItemService foodItemService;

	@Autowired
	IDiaryEntryService diaryEntryService;

	private DiaryEntry diaryEntry;

	@Before
	public void init ()
			throws Exception {

		List<FoodItem> foodItems = new ArrayList<FoodItem> ();
		foodItems.add (foodItemService.get ("Banana").get ());
		foodItems.add (foodItemService.get ("Egg, fried").get ());

		DiaryEntry diaryEntry = new DiaryEntry ();
		diaryEntry.setFoodItems (foodItems);

		diaryEntry.setTime (LocalDateTime.now ());
		this.diaryEntry = diaryEntryService.add (diaryEntry);

	}

	@Test
	public void addObjectsTest ()
			throws Exception {

		DiaryEntry diaryEntry = this.diaryEntry;
		
		diaryEntry.computeNutritionalInformation ();
		
		logger.info ("After compute: {}", diaryEntry.getTotalNutrionalnformation ().getCalories ());
		
		diaryEntry.applyServingsModifiers ();
		diaryEntry.computeNutritionalInformation ();
		
		logger.info ("After serving modifier: {}", diaryEntry.getTotalNutrionalnformation ().getCalories ());


		
		diaryEntry.getTotalNutrionalnformation ().getCalories ();

	}

	private static Logger logger = LoggerFactory.getLogger (DiaryEntryHelperTests.class);

}
