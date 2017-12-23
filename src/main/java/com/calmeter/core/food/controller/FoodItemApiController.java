package com.calmeter.core.food.controller;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.calmeter.core.food.source.handler.IExternalFoodSourceHandler;
import com.calmeter.core.reader.BarCodeReader;
import com.calmeter.core.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.calmeter.core.account.model.User;
import com.calmeter.core.account.utils.UserHelper;
import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.service.IFoodItemEntryService;
import com.calmeter.core.food.service.IFoodItemService;
import com.calmeter.core.food.source.handler.IFoodSourceHandler;
import com.calmeter.core.food.source.model.FoodSource;
import com.calmeter.core.food.source.service.IFoodSourceService;
import com.calmeter.core.food.source.utils.FoodSourceHelper;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

@RestController
@RequestMapping("/api/food-item")
public class FoodItemApiController {

    @Autowired
    UserHelper userHelper;

    @Autowired
    IFoodItemService foodItemService;

    @Autowired
    IFoodSourceService foodSourceService;

    @Autowired
    IFoodItemEntryService foodItemEntryService;

    @Autowired
    FoodSourceHelper foodSourceHelper;

    @Autowired
    BarCodeReader barCodeReader;

    @Autowired
    FileUtils fileUtils;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<FoodItem> getFoodItem(@PathVariable Integer id) {

        Optional<User> userWrapper = userHelper.getLoggedInUser();
        if (!userWrapper.isPresent()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Optional<FoodItem> foodItemWrapper = foodItemService.get(id);
        if (!foodItemWrapper.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        FoodItem foodItem = foodItemWrapper.get();

        if (!foodItem.getCreator().equals(userWrapper.get())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(foodItem, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    ResponseEntity<Collection<FoodItem>> getUserFoodItems() {

        Optional<User> userWrapper = userHelper.getLoggedInUser();
        if (!userWrapper.isPresent()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<FoodItem> foodItems = foodItemService.getAll(userWrapper.get());
        return new ResponseEntity<>(foodItems, HttpStatus.OK);

    }

    @RequestMapping(value = "/searchFood", method = RequestMethod.GET)
    ResponseEntity<Collection<FoodItem>> searchFood(@RequestParam("query") String query,
                                                    @RequestParam("foodSource") String inputFoodSource) {

        Optional<User> userWrapper = userHelper.getLoggedInUser();
        if (!userWrapper.isPresent()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Optional<FoodSource> foodSourceWrapper = foodSourceService.findByName(inputFoodSource);
        if (!foodSourceWrapper.isPresent())
            return new ResponseEntity<Collection<FoodItem>>(HttpStatus.METHOD_NOT_ALLOWED);

        IFoodSourceHandler foodSourceHandler = foodSourceHelper.getFoodSourceHandler(foodSourceWrapper.get());

        List<FoodItem> foundFoodItems = foodSourceHandler.search(query, userWrapper.get());
        return new ResponseEntity<Collection<FoodItem>>(foundFoodItems, HttpStatus.OK);
    }

    @RequestMapping(value = "/processImage", method = RequestMethod.POST)
    ResponseEntity<FoodItem> processImage(@RequestParam("uploadFile") MultipartFile multipartFile) {

        Optional<String> barCodeWrapper = Optional.empty();
        try {
            InputStream in = new ByteArrayInputStream(multipartFile.getBytes());
            BufferedImage bufferedImage = ImageIO.read(in);
            barCodeWrapper = barCodeReader.getCodeFromImage(bufferedImage);
            in.close();

        } catch (IOException e) {
            new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        if (!barCodeWrapper.isPresent()) {
            logger.info("Unable to get barcode from file");
            new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        logger.info("Found barcode: {}", barCodeWrapper.get());

        Optional<FoodItem> foodItemWrapper = Optional.empty();
        for (FoodSource foodSource : foodSourceService.getExternalSources()) {

            logger.info("Getting foodItem using source: {}", foodSource.getName());

            IFoodSourceHandler foodSourceHandler = foodSourceHelper.getFoodSourceHandler(foodSource);
            IExternalFoodSourceHandler externalFoodSourceHandler = (IExternalFoodSourceHandler) foodSourceHandler;

            foodItemWrapper = externalFoodSourceHandler.getItemFromGtin(Long.valueOf(barCodeWrapper.get()));
            if (foodItemWrapper.isPresent())
                break;

        }

        if (!foodItemWrapper.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<FoodItem>(foodItemWrapper.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/createFoodItem", method = RequestMethod.POST)
    ResponseEntity<?> createFoodItem(@RequestBody FoodItem foodItem) {

        Optional<User> userWrapper = userHelper.getLoggedInUser();
        if (!userWrapper.isPresent()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        foodItem.setCreator(userWrapper.get());
        foodItemService.save(foodItem);
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/updateFoodItem/{id}", method = RequestMethod.POST)
    ResponseEntity<FoodItem> updateFoodItem(@PathVariable("id") Long id, @RequestBody FoodItem foodItem) {

        Optional<User> userWrapper = userHelper.getLoggedInUser();
        if (!userWrapper.isPresent()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Optional<FoodItem> originalFoodItemWrapper = foodItemService.get(id);
        if (!originalFoodItemWrapper.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!originalFoodItemWrapper.get().getCreator().equals(userWrapper.get())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(foodItemService.save(foodItem), HttpStatus.OK);

    }

    @RequestMapping(value = "/deleteFoodItem/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteFoodItem(@PathVariable("id") Long id) {

        Optional<User> userWrapper = userHelper.getLoggedInUser();
        if (!userWrapper.isPresent()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Optional<FoodItem> originalFoodItemWrapper = foodItemService.get(id);
        if (!originalFoodItemWrapper.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        FoodItem foodItem = originalFoodItemWrapper.get();

        if (!foodItem.getCreator().equals(userWrapper.get())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (foodItemService.isUsed(foodItem)) {
            foodItem.setDisabled(true);
            foodItemService.save(foodItem);
            return new ResponseEntity<String>("FoodItem disabled: " + id, HttpStatus.CREATED);
        }

        foodItemService.delete(foodItem);
        return new ResponseEntity<String>("FoodItem deleted: " + id, HttpStatus.OK);
    }

    public static final Logger logger = LoggerFactory.getLogger(FoodItemApiController.class);

}
