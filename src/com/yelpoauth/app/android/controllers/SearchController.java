package com.yelpoauth.app.android.controllers;

import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.yelpoauth.app.android.helpers.U;
import com.yelpoauth.app.android.models.Business;
import com.yelpoauth.app.android.oauth.Yelp;
import com.yelpoauth.app.android.oauth.YelpCredentials;

public class SearchController {
	private Yelp mYelpClient;
	private Context mContext;

	public SearchController(Context c){
		mYelpClient = new Yelp(YelpCredentials.CONSUMER_KEY, YelpCredentials.CONSUMER_SECRET, YelpCredentials.TOKEN, YelpCredentials.TOKEN_SECRET);
		mContext = c;
	}
	
	public JSONObject getSearchResponse(String query){
		Location location = U.getCurrentLocation(mContext);
		String response = mYelpClient.search(query , location.getLatitude(), location.getLongitude());
		JSONObject responseObject = U.getObject(response);
		return responseObject;
	}
//	
//	public List<Business> getBusinessList(JSONObject inputJSON){
//		
//	}
}
