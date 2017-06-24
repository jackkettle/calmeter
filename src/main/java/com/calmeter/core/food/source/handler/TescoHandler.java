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

import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.source.config.FoodSourceConfig;
import com.calmeter.core.food.source.handler.helper.TescoHandlerHelper;

@Component("tescoHandler")
public class TescoHandler
		implements IFoodSourceHandler {

	public static final int OFFSET = 0;

	public static final int SEARCH_LIMIT = 3;

	public static final String SEARCH_API_URL = "https://dev.tescolabs.com/grocery/products/";

	public static final String PRODUCT_API_URL = "https://dev.tescolabs.com/product/";

	@Autowired
	FoodSourceConfig foodSourceConfig;

	@Override
	public FoodItem getItemFromID (Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FoodItem> search (String searchQuery) {

		HttpClient httpclient = HttpClients.createDefault ();

		List<FoodItem> foodList = new ArrayList<> ();
		URIBuilder builder = null;
		try {
			builder = new URIBuilder (SEARCH_API_URL);

			builder.addParameter ("query", searchQuery);
			builder.addParameter ("offset", OFFSET + "");
			builder.setParameter ("limit", SEARCH_LIMIT + "");

			URI uri = builder.build ();
			HttpGet request = new HttpGet (uri);

			request.setHeader ("Ocp-Apim-Subscription-Key", foodSourceConfig.getTescoSubscriptionKey ());

			HttpResponse response = httpclient.execute (request);
			HttpEntity entity = response.getEntity ();

			if (entity == null)
				return foodList;

			Collection<String> numbers = TescoHandlerHelper.getTescoProductNumbersFromJson (EntityUtils.toString (entity));
			logger.info ("{}", String.join (",", numbers));

			this.getFoodItemsFromTescoProductNumber (numbers);

		}
		catch (URISyntaxException | ParseException | IOException e) {
			logger.error ("Unable to build URI: {}", SEARCH_API_URL, e);
			return foodList;
		}

		return foodList;
	}

	public List<FoodItem> getFoodItemsFromTescoProductNumber (Collection<String> numbers) {

		HttpClient httpclient = HttpClients.createDefault ();

		List<FoodItem> foodList = new ArrayList<> ();
		URIBuilder builder = null;
		try {
			builder = new URIBuilder (PRODUCT_API_URL);

			for (String id : numbers) {
				builder.addParameter ("tpnb", id);
			}

			URI uri = builder.build ();
			HttpGet request = new HttpGet (uri);

			request.setHeader ("Ocp-Apim-Subscription-Key", foodSourceConfig.getTescoSubscriptionKey ());

			HttpResponse response = httpclient.execute (request);
			HttpEntity entity = response.getEntity ();

			if (entity == null)
				return foodList;

			logger.info ("===========BREAKPOINT===============");
			logger.info (EntityUtils.toString (entity));

		}
		catch (URISyntaxException | ParseException | IOException e) {
			logger.error ("Unable to build URI: {}", SEARCH_API_URL, e);
			return foodList;
		}
		return foodList;
	}

	private static Logger logger = LoggerFactory.getLogger (TescoHandler.class);

}
