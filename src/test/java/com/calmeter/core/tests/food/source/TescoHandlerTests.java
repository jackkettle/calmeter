package com.calmeter.core.tests.food.source;

import java.util.List;

import com.calmeter.core.ApplicationConfig;
import com.calmeter.core.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.source.handler.TescoHandler;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.junit.Assert.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
@ContextConfiguration(classes = {Main.class, ApplicationConfig.class})
public class TescoHandlerTests extends AbstractTestNGSpringContextTests {

    @Autowired
    @Qualifier("tescoHandler")
    TescoHandler tescoHandler;

    @Test
    public void searchTest() throws Exception {

        String query = "egg";

        List<FoodItem> foodItems = tescoHandler.search(query, null);
        for (FoodItem foodItem : foodItems) {
            logger.info("foodItem name: {}", foodItem.getName());
        }

        assertTrue(foodItems.size() > 1);
    }

    private static Logger logger = LoggerFactory.getLogger(TescoHandlerTests.class);

}
