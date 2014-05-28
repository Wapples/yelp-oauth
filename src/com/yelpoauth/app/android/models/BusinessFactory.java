package com.yelpoauth.app.android.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.yelpoauth.app.android.helpers.U;

public class BusinessFactory {
	
	public static String RATING_IMG_URL_LARGE = "rating_img_url_large";
	public static String SNIPPET_TEXT = "snippet_text";
	public static String DISPLAY_PHONE = "display_phone";
	public static String PHONE = "phone";
	public static String STATE_CODE = "state_code";
	public static String COUNTRY_CODE = "country_code";
	public static String POSTAL_CODE = "postal_code";
	public static String CITY = "city";
	public static String REVIEW_COUNT = "review_count";
	public static String IS_CLOSED = "is_closed";
	public static String YELP_ID = "id";
	public static String DISTANCE = "distance";
	public static String IMAGE_URL = "image_url";
	public static String NAME = "name";
	public static String RATING = "rating";
	public static String CATEGORIES = "categories";
	public static String MOBILE_URL = "mobile_url";
	public static String URL = "url";
	public static String STREET_ADDRESS = "address";
	
	public static List<Business> getBusinessList(JSONObject input){
		List<Business> businessList = new ArrayList<Business>();
		JSONArray businessArray = U.ga(input, "businesses");
		Log.i("BUSINESS LIST", "length: " + businessArray.length());
		for (int i = 0; i < businessArray.length(); i++){
			Business business = new Business();
			try {
				JSONObject businessObject = businessArray.getJSONObject(i);
				if (!U.gs(businessObject, RATING_IMG_URL_LARGE).equals(null)){
					business.ratingImageUrl = U.gs(businessObject, RATING_IMG_URL_LARGE);
				}
				if (!U.gs(businessObject, SNIPPET_TEXT).equals(null)){
					business.snippetText = U.gs(businessObject, SNIPPET_TEXT);
				}
				if (!U.gs(businessObject, DISPLAY_PHONE).equals(null)){
					business.displayPhone = U.gs(businessObject, DISPLAY_PHONE);
				}
				if (!U.gs(businessObject, PHONE).equals(null)){
					business.phone = U.gs(businessObject, PHONE);
				}
				if (!U.gs(businessObject, CITY).equals(null)){
					business.city = U.gs(businessObject, CITY);
				}
				if (!U.gs(businessObject, STATE_CODE).equals(null)){
					business.state = U.gs(businessObject, STATE_CODE);
				}
				if (!U.gs(businessObject, COUNTRY_CODE).equals(null)){
					business.country = U.gs(businessObject, COUNTRY_CODE);
				}
				if (!U.gs(businessObject, POSTAL_CODE).equals(null)){
					business.zip = U.gs(businessObject, POSTAL_CODE);
				}
				if (!U.gs(businessObject, REVIEW_COUNT).equals(null)){
					business.reviewCount = U.gi(businessObject, REVIEW_COUNT);
				}
				if (!U.gs(businessObject, IS_CLOSED).equals(null)){
					business.isClosed = U.gb(businessObject, IS_CLOSED);
				}
				if (!U.gs(businessObject, YELP_ID).equals(null)){
					business.yelpId = U.gs(businessObject, YELP_ID);
				}
				if (!U.gs(businessObject, DISTANCE).equals(null)){
					business.distance = U.gd(businessObject, DISTANCE);
				}
				if (!U.gs(businessObject, IMAGE_URL).equals(null)){
					business.imageUrl = U.gs(businessObject, IMAGE_URL);
				}
				if (!U.gs(businessObject, NAME).equals(null)){
					business.name = U.gs(businessObject, NAME);
				}
				if (!U.gs(businessObject, RATING).equals(null)){
					business.rating = U.gd(businessObject, RATING);
				}
				if (!U.gs(businessObject, MOBILE_URL).equals(null)){
					business.mobileUrl = U.gs(businessObject, MOBILE_URL);
				}
				if (!U.gs(businessObject, URL).equals(null)){
					business.url = U.gs(businessObject, URL);
				}
				if (U.ga(businessObject, CATEGORIES).length() > 0){
					business.categories = U.getStringArrayList(U.ga(businessObject, CATEGORIES));
				}
				if (U.ga(businessObject, STREET_ADDRESS).length() > 0){
					business.streetAddress = U.getStringFromArray(U.ga(businessObject, STREET_ADDRESS));
				}
				businessList.add(business);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return businessList;
	}
}
