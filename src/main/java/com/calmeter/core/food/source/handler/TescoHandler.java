package com.calmeter.core.food.source.handler;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.calmeter.core.account.model.User;
import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.FoodItemType;
import com.calmeter.core.food.service.IFoodItemService;
import com.calmeter.core.food.source.config.FoodSourceConfig;
import com.calmeter.core.food.source.handler.helper.TescoHandlerHelper;

@Component("tescoHandler")
public class TescoHandler
        implements IFoodSourceHandler, IExternalFoodSourceHandler {

    private static final int OFFSET = 0;

    private static final int SEARCH_LIMIT = 10;

    private static final String SEARCH_API_URL = "https://dev.tescolabs.com/grocery/products/";

    private static final String PRODUCT_API_URL = "https://dev.tescolabs.com/product/";

    @Autowired
    FoodSourceConfig foodSourceConfig;

    @Autowired
    IFoodItemService foodItemService;

    @Override
    public Optional<FoodItem> getItemFromID(Long id) {
        HttpClient httpclient = HttpClients.createDefault();

        URIBuilder builder;
        try {
            builder = new URIBuilder(PRODUCT_API_URL);
            builder.addParameter("tpnb", id + "");

            URI uri = builder.build();
            HttpGet request = new HttpGet(uri);
            request.setHeader("Ocp-Apim-Subscription-Key", foodSourceConfig.getTescoSubscriptionKey());

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity == null)
                return Optional.empty();

            List<FoodItem> foodItems = TescoHandlerHelper.getFoodItemsFromResponse(EntityUtils.toString(entity));
            logger.info("Total food items found: {}", foodItems.size());

            if (foodItems.size() > 0)
                return Optional.of(foodItems.get(0));

            return Optional.empty();

        } catch (URISyntaxException | ParseException | IOException e) {
            logger.error("Unable to build URI: {}", SEARCH_API_URL, e);
            return Optional.empty();
        }

    }

    @Override
    public Optional<FoodItem> getItemFromGtin(Long gtin) {
        HttpClient httpclient = HttpClients.createDefault();

        URIBuilder builder;
        try {
            builder = new URIBuilder(PRODUCT_API_URL);
            builder.addParameter("gtin", gtin + "");

            URI uri = builder.build();
            HttpGet request = new HttpGet(uri);
            request.setHeader("Ocp-Apim-Subscription-Key", foodSourceConfig.getTescoSubscriptionKey());

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity == null)
                return Optional.empty();

            List<FoodItem> foodItems = TescoHandlerHelper.getFoodItemsFromResponse(EntityUtils.toString(entity));
            logger.info("Total food items found: {}", foodItems.size());
            if (foodItems.size() > 0)
                return Optional.of(foodItems.get(0));

            return Optional.empty();

        } catch (URISyntaxException | ParseException | IOException e) {
            logger.error("Unable to build URI: {}", SEARCH_API_URL, e);
            return Optional.empty();
        }

    }

    @Override
    public List<FoodItem> search(String searchQuery, User user) {

        HttpClient httpclient = HttpClients.createDefault();

        List<FoodItem> foodList = new ArrayList<>();
        URIBuilder builder;
        try {
            builder = new URIBuilder(SEARCH_API_URL);

            builder.addParameter("query", searchQuery);
            builder.addParameter("offset", OFFSET + "");
            builder.setParameter("limit", SEARCH_LIMIT + "");

            URI uri = builder.build();
            HttpGet request = new HttpGet(uri);

            request.setHeader("Ocp-Apim-Subscription-Key", foodSourceConfig.getTescoSubscriptionKey());

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity == null)
                return foodList;

            Collection<String> numbers = TescoHandlerHelper.getTescoProductNumbersFromJson(EntityUtils.toString(entity));
            logger.info("Found IDs: {}", String.join(",", numbers));

            List<FoodItem> existingFoodItems = getExistingFoodItems(numbers);
            logger.info("Total existing foodItems Found in DB: {}", existingFoodItems.size());

            foodList.addAll(existingFoodItems);

            for (FoodItem foodItem : existingFoodItems) {

                String exId = Long.toString(foodItem.getExternalId());
                if (numbers.contains(exId)) {
                    logger.debug("Removing found tpub from list: {}", exId);
                    numbers.remove(exId);
                }
            }

            logger.info("Total foodItems to add to get from response: {}", existingFoodItems.size());
            foodList.addAll(getFoodItemsFromTescoProductNumber(numbers));

        } catch (URISyntaxException | ParseException | IOException e) {
            logger.error("Unable to build URI: {}", SEARCH_API_URL, e);
            return foodList;
        }

        return foodList;
    }

    private List<FoodItem> getExistingFoodItems(Collection<String> numbers) {
        List<FoodItem> foodItems = new ArrayList<>();
        for (String externalId : numbers) {

            Long id = Long.valueOf(externalId);
            if (!foodItemService.existsExternal(id, FoodItemType.TESCO_ITEM))
                continue;

            Optional<FoodItem> foodItemWrapper = foodItemService.getExternal(id, FoodItemType.TESCO_ITEM);
            if (!foodItemWrapper.isPresent())
                continue;

            foodItems.add(foodItemWrapper.get());
        }
        return foodItems;
    }

    private List<FoodItem> getFoodItemsFromTescoProductNumber(Collection<String> numbers) {

        HttpClient httpclient = HttpClients.createDefault();

        List<FoodItem> foodList = new ArrayList<>();
        URIBuilder builder;
        try {
            builder = new URIBuilder(PRODUCT_API_URL);

            for (String id : numbers) {
                builder.addParameter("tpnb", id);
            }

            URI uri = builder.build();
            HttpGet request = new HttpGet(uri);

            request.setHeader("Ocp-Apim-Subscription-Key", foodSourceConfig.getTescoSubscriptionKey());

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity == null)
                return foodList;

            return TescoHandlerHelper.getFoodItemsFromResponse(EntityUtils.toString(entity));

        } catch (URISyntaxException | ParseException | IOException e) {
            logger.error("Unable to build URI: {}", SEARCH_API_URL, e);
            return foodList;
        }

    }

    private static Logger logger = LoggerFactory.getLogger(TescoHandler.class);

}
