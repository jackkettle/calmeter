package com.calmeter.core.food.source.handler;

import com.calmeter.core.account.model.User;
import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.source.handler.helper.OpenFoodFactsHelper;
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
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OpenFoodFactsHandler implements IFoodSourceHandler, IExternalFoodSourceHandler {

    private static final String PRODUCT_API_URL = "https://world.openfoodfacts.org/api/v0/product/";

    private static final String SEARCH_API_URL = "https://uk.openfoodfacts.org/cgi/search.pl?&search_simple=1&action=process&json=1";

    @Override
    public Optional<FoodItem> getItemFromGtin(Long id) {
        HttpClient httpclient = HttpClients.createDefault();

        URIBuilder builder;
        try {
            builder = new URIBuilder(PRODUCT_API_URL);
            builder.setPath(builder.getPath() + String.valueOf(id) + ".json");
            URI uri = builder.build();
            HttpGet request = new HttpGet(uri);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity == null)
                return Optional.empty();

            return OpenFoodFactsHelper.getFoodItemFromResponse(EntityUtils.toString(entity));

        } catch (URISyntaxException | ParseException | IOException e) {
            logger.error("Unable to build URI: {}", SEARCH_API_URL, e);
            return Optional.empty();
        }

    }

    @Override
    public Optional<FoodItem> getItemFromID(Long id) {
        return this.getItemFromGtin(id);
    }

    @Override
    public List<FoodItem> search(String search, User user) {

        HttpClient httpclient = HttpClients.createDefault();

        URIBuilder builder;
        try {
            builder = new URIBuilder(SEARCH_API_URL);
            builder.addParameter("search_terms", search);

            URI uri = builder.build();
            HttpGet request = new HttpGet(uri);

            logger.info("URI: {}", uri.toString());

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity == null)
                return new ArrayList<>();

            List<FoodItem> foodItems = OpenFoodFactsHelper.getFoodItemsFromResponse(EntityUtils.toString(entity));

            if (foodItems == null)
                return new ArrayList<>();

            return foodItems;

        } catch (URISyntaxException | ParseException | IOException e) {
            logger.error("Unable to build URI: {}", SEARCH_API_URL, e);
            return new ArrayList<>();
        }

    }

    private static Logger logger = LoggerFactory.getLogger(OpenFoodFactsHandler.class);
}
