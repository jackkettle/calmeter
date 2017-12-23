package com.calmeter.core.tests.food.source;

import java.util.List;
import java.util.Optional;

import com.calmeter.core.ApplicationConfig;
import com.calmeter.core.Main;
import com.calmeter.core.food.source.handler.OpenFoodFactsHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import com.calmeter.core.food.model.FoodItem;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
@ContextConfiguration(classes = {Main.class, ApplicationConfig.class})
public class OpenFoodFactsTests extends AbstractTestNGSpringContextTests {

    @Autowired
    OpenFoodFactsHandler openFoodFactsHandler;

    @Test
    public void getFoodItemFromGtinTest1() {

        String gtin = "5000159459228";

        Optional<FoodItem> foodItemWrapper = openFoodFactsHandler.getItemFromGtin(Long.valueOf(gtin));
        Assert.assertTrue(foodItemWrapper.isPresent());
        Assert.assertEquals("Twix", foodItemWrapper.get().getName());
        Assert.assertTrue(foodItemWrapper.get().getNutritionalInformation().isValid());
    }

    @Test
    public void searchTest1() {

        String searchTerm = "egg";

        List<FoodItem> foodItems = openFoodFactsHandler.search(searchTerm, null);
        Assert.assertTrue(foodItems.size() > 0);
    }

    private static Logger logger = LoggerFactory.getLogger(TescoHandlerTests.class);

}
