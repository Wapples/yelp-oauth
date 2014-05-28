package com.yelpoauth.app.android.models;

import java.util.List;
import java.util.Map;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "Businesses")
public class Business extends Model{
	public static String QUERY = "query";
	public static String ID = "id";
	public static String NAME = "name";
	public static String IMAGE_URL = "imageUrl";
	public static String URL = "url";
	public static String MOBILE_URL = "mobile_url";
	public static String PHONE = "phone";
	public static String DISPLAY_PHONE = "displayPhone";
	public static String REVIEW_COUNT = "reviewCount";
	public static String DISTANCE = "distance";
	public static String RATING = "rating";
	public static String SNIPPET_TEXT = "snippetText";
	public static String RATING_IMAGE_URL = "ratingImageUrl";

	
	@Column(name = "Query")
	public Query query;
	
	@Column(name = "yelpId")
	public String yelpId;

	@Column(name = "name")
	public String name;
	
	@Column(name = "imageUrl")
	public String imageUrl;
	
	@Column(name = "url")
	public String url;
	
	@Column(name = "mobileUrl")
	public String mobileUrl;
	
	@Column(name = "phone")
	public String phone;
	
	@Column(name = "displayPhone")
	public String displayPhone;
	
	@Column(name = "reviewCount")
	public int reviewCount;
	
	@Column(name = "distance")
	public int distance;
	
	@Column(name = "rating")
	public double rating;
	
	@Column(name = "snippetText")
	public String snippetText;
	
	@Column(name = "ratingImageUrl")
	public String ratingImageUrl;

	
	public Business(){
			super();
			
	}
	
	public void storeSingle(Map<String, Object> constructorMap){
		Business business = new Business();
		business.query = (Query) constructorMap.get(QUERY);
		business.yelpId = (String) constructorMap.get(ID);
		business.name = (String) constructorMap.get(NAME);
		business.imageUrl = (String) constructorMap.get(IMAGE_URL);
		business.url = (String) constructorMap.get(URL);
		business.mobileUrl = (String) constructorMap.get(MOBILE_URL);
		business.phone = (String) constructorMap.get(PHONE);
		business.displayPhone = (String) constructorMap.get(DISPLAY_PHONE);
		business.reviewCount = (Integer) constructorMap.get(REVIEW_COUNT);
		business.distance = (Integer) constructorMap.get(DISTANCE);
		business.rating = (Double) constructorMap.get(RATING);
		business.snippetText = (String) constructorMap.get(SNIPPET_TEXT);
		business.ratingImageUrl = (String) constructorMap.get(RATING_IMAGE_URL);
		business.save();
	}

	public static List<Business> getAll(Query query) {
	    return new Select()
	        .from(Business.class)
	        .where(QUERY + "= ?", query.getId())
	        .orderBy(NAME +"ASC")
	        .execute();
	}
	 

}
