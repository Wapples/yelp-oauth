package com.yelpoauth.app.android.oauth;

/*
 Example code based on code from Nicholas Smith at http://imnes.blogspot.com/2011/01/how-to-use-yelp-v2-from-java-including.html
 For a more complete example (how to integrate with GSON, etc) see the blog post above.
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.signature.HmacSha1MessageSigner;
import oauth.signpost.signature.QueryStringSigningStrategy;

import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import android.net.Uri;
import android.text.format.Time;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.yelpoauth.android.application.VolleyController;
import com.yelpoauth.app.android.helpers.CustomJSONObjectRequest;

public class VolleyYelpClient {

	/**
	 * Setup the Yelp API OAuth credentials.
	 * 
	 * OAuth credentials are available from the developer site, under Manage API
	 * access (version 2 API).
	 * 
	 * @param consumerKey
	 *            Consumer key
	 * @param consumerSecret
	 *            Consumer secret
	 * @param token
	 *            Token
	 * @param tokenSecret
	 *            Token secret
	 */
	public VolleyYelpClient() {
		// this.service = new
		// ServiceBuilder().provider(YelpApi2.class).apiKey(consumerKey).apiSecret(consumerSecret).build();
		super();
	}

	/**
	 * Search with term and location.
	 * 
	 * @param term
	 *            Search term
	 * @param latitude
	 *            Latitude
	 * @param longitude
	 *            Longitude
	 * @return JSON string response
	 */
	public static void search(String term, String latitude, String longitude,
			Listener<JSONObject> responseSuccessListener,
			ErrorListener responseErrorListener) {

		String requestUrl = String.format(
				"http://api.yelp.com/v2/search?term=%s&ll=%s,%s",
				Uri.encode(term), latitude + "", longitude + "");
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(
				YelpCredentials.CONSUMER_KEY, YelpCredentials.CONSUMER_SECRET);
		consumer.setMessageSigner(new HmacSha1MessageSigner());
		consumer.setTokenWithSecret(YelpCredentials.TOKEN,
				YelpCredentials.TOKEN_SECRET);
		consumer.setSendEmptyTokens(true);
		consumer.setSigningStrategy(new QueryStringSigningStrategy());
		String signedQuery = "";
		try {
			signedQuery = consumer.sign(requestUrl);
		} catch (OAuthMessageSignerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JsonObjectRequest request = new JsonObjectRequest(Method.GET,
				signedQuery, null, responseSuccessListener,
				responseErrorListener);

		VolleyController.getInstance().addToRequestQueue(request);
	}
}