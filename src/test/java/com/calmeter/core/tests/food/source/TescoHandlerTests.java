package com.calmeter.core.tests.food.source;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.source.handler.TescoHandler;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TescoHandlerTests {

	@Autowired
	@Qualifier("tescoHandler")
	TescoHandler tescoHandler;

	@Test
	public void searchTest ()
			throws Exception {

		String query = "egg";

		List<FoodItem> foodItems = tescoHandler.search(query, null);
		for (FoodItem foodItem : foodItems) {
			logger.info ("foodItem name: {}", foodItem.getName ());
		}

        assertTrue(foodItems.size() > 1);


    }

	private static Logger logger = LoggerFactory.getLogger (TescoHandlerTests.class);

}
