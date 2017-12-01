package com.calmeter.core.tests.food.controller;

import com.calmeter.core.ApplicationConfig;
import com.calmeter.core.Main;
import com.calmeter.core.custom.TestValueLoader;
import com.calmeter.core.food.controller.FoodItemApiController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
@ContextConfiguration(classes = {Main.class, ApplicationConfig.class})
public class FoodItemApiControllerTests extends AbstractTestNGSpringContextTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    TestValueLoader testValueLoader;

    @BeforeClass
    public void setUp() {
        this.testValueLoader.loadValues();
    }

    @Test
    public void getFoodItemsTest() throws Exception {
        String body = this.restTemplate.getForObject("/api/food/getAll", String.class);
        logger.info("{}", body);

    }

    @Test
    public void doNotGetDisabledFoodItemsTest() {

    }


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    private static Logger logger = LoggerFactory.getLogger(FoodItemApiControllerTests.class);


}
