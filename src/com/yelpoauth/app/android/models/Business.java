package com.yelpoauth.app.android.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "Businesses")
public class Business  extends Model implements Serializable {
	public static String QUERY = "query";
	
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
	
	@Column(name = "state")
	public String state;
	
	@Column(name = "city")
	public String city;
	
	@Column(name = "zip")
	public String zip;
	
	@Column(name = "country")
	public String country;
	
	@Column(name = "mobileUrl")
	public String mobileUrl;
	
	@Column(name = "phone")
	public String phone;
	
	@Column(name = "displayPhone")
	public String displayPhone;
	
	@Column(name = "reviewCount")
	public Integer reviewCount;
	
	@Column(name = "distance")
	public Double distance;
	
	@Column(name = "rating")
	public Double rating;
	
	@Column(name = "snippetText")
	public String snippetText;
	
	@Column(name = "ratingImageUrl")
	public String ratingImageUrl;
	
	@Column(name = "isClosed")
	public Boolean isClosed;
	
	@Column(name = "latitude")
	public Boolean latitude;
	
	@Column(name = "longitude")
	public Boolean longitude;
	
	@Column(name = "streetAddress")
	public String streetAddress;
	
	@Column(name = "categories")
	public ArrayList<String> categories;

	public Business(){
			super();
	}
	
	public static void storeAll(List<Business> input){
		for(Business business : input){
			if (!contains(business.name)){
				business.save();
			}
		}
	}

	public static List<Business> getAllByQuery(Query query) {
	    return new Select()
	        .from(Business.class)
	        .where(QUERY + "= ?", query.getId())
	        .orderBy(BusinessFactory.NAME +" ASC")
	        .execute();
	}
	
	public static List<Business> getAll() {
	    return new Select()
	        .from(Business.class)
	        .orderBy(BusinessFactory.DISTANCE + " ASC")
	        .execute();
	}
	
	public static Boolean contains(String name){
		Boolean ret = false;
		List<Business> list = new Select()
	        .from(Business.class)
	        .where(BusinessFactory.NAME + "= ?", name)
	        .orderBy(BusinessFactory.DISTANCE + " ASC")
	        .execute();
		if (list.size() > 0){
			ret = true;
		}
		return ret;
	}
	 

}
