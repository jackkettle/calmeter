package com.calmeter.core.food.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.calmeter.core.food.utils.MealHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.calmeter.core.account.model.User;
import com.calmeter.core.account.utils.UserHelper;
import com.calmeter.core.food.model.Meal;
import com.calmeter.core.food.service.IMealService;

@RestController
@RequestMapping("/api/meal")
public class MealApiController {

    UserHelper userHelper;
    MealHelper mealHelper;
    IMealService mealService;

    @Autowired
    public MealApiController(UserHelper userHelper, MealHelper mealHelper, IMealService mealService) {
        this.userHelper = userHelper;
        this.mealHelper = mealHelper;
        this.mealService = mealService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<Meal> get(@PathVariable Long id) {

        Optional<User> userWrapper = userHelper.getLoggedInUser();
        if (!userWrapper.isPresent()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Optional<Meal> mealWrapper = mealService.get(id);
        if (!mealWrapper.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Meal meal = mealWrapper.get();

        if (!meal.getCreator().equals(userWrapper.get())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(meal, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    ResponseEntity<Collection<Meal>> getAll() {

        Optional<User> userWrapper = userHelper.getLoggedInUser();
        if (!userWrapper.isPresent()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<Meal> meals = mealService.getAll(userWrapper.get());
        return new ResponseEntity<>(meals, HttpStatus.OK);

    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    ResponseEntity<String> create(@RequestBody Meal meal) {

        Optional<User> userWrapper = userHelper.getLoggedInUser();
        if (!userWrapper.isPresent()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (!mealHelper.isValid(meal)) {
            logger.info("Meal is invalid");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(mealService.existsByName(meal.getName()))
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        meal.computeNutritionalInformation();

        meal.setCreator(userWrapper.get());
        mealService.save(meal);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    ResponseEntity<Meal> updateFoodItem(@PathVariable("id") Long id, @RequestBody Meal meal) {

        Optional<User> userWrapper = userHelper.getLoggedInUser();
        if (!userWrapper.isPresent()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Optional<Meal> mealWrapper = mealService.get(id);
        if (!mealWrapper.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!mealWrapper.get().getCreator().equals(userWrapper.get())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        meal.setId(id);
        meal.setCreator(userWrapper.get());
        Meal updatedMeal = mealService.save(meal);
        return new ResponseEntity<>(updatedMeal, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    ResponseEntity<String> delete(@PathVariable("id") Long id) {

        Optional<User> userWrapper = userHelper.getLoggedInUser();
        if (!userWrapper.isPresent()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Optional<Meal> mealWrapper = mealService.get(id);
        if (!mealWrapper.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Meal meal = mealWrapper.get();

        if (!meal.getCreator().equals(userWrapper.get())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (mealService.isUsed(meal)) {
            meal.setDisabled(true);
            mealService.save(meal);
            return new ResponseEntity<String>("Meal disabled: " + id, HttpStatus.CREATED);
        }

        mealService.delete(meal);
        return new ResponseEntity<String>("Meal deleted: " + id, HttpStatus.OK);
    }

    public static final Logger logger = LoggerFactory.getLogger(FoodItemApiController.class);

}
