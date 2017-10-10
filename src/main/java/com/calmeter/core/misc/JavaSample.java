package com.calmeter.core.misc;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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


public class JavaSample 
{
	public static final int SEARCH_LIMIT = 20;

	public static final String SEARCH_API_URL = "https://dev.tescolabs.com/grocery/products/";
	
    public static void main(String[] args) 
    {
		HttpClient httpclient = HttpClients.createDefault ();

		URIBuilder builder = null;
		try {
			builder = new URIBuilder (SEARCH_API_URL);

			builder.addParameter ("query", "egg");
			// builder.addParameter ("offset", "");
			builder.setParameter ("limit", SEARCH_LIMIT + "");

			URI uri = builder.build ();
			HttpGet request = new HttpGet (uri);
			request.setHeader ("Ocp-Apim-Subscription-Key", "{subscription key}");

			HttpResponse response = httpclient.execute (request);
			HttpEntity entity = response.getEntity ();

			if (entity != null) {
				System.out.println (EntityUtils.toString (entity));
			}

		}
		catch (URISyntaxException | ParseException | IOException e) {
			logger.error ("Unable to build URI: {}", SEARCH_API_URL, e);
		}
    }
    
	private static Logger logger = LoggerFactory.getLogger (JavaSample.class);

}
