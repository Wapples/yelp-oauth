package com.yelpoauth.app.android.helpers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;

public class U {

	// Ask User for Zip Code if no gps found
	// hard-coded location for when no other location can be found.
	public final static double LATITUDE  =   33.681278;
	public final static double LONGITUDE = -117.891411;
	public final static String BASE_URL = "https://staging.weedmaps.com/api/v4/";
	
	// name of SharedPreferences object    
    FragmentManager fm;
    
    public static Location getCurrentLocation(Context context) {
		LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		Criteria c = new Criteria();
		String provider = lm.getBestProvider(c, true);
		Location location;
		if (provider == null) {
			location = new Location("gps");
			location.setLatitude(U.LATITUDE);
			location.setLongitude(U.LONGITUDE);
		} else {
			location = lm.getLastKnownLocation(provider);
			if (location == null) {
				location = new Location("gps");
				location.setLatitude(U.LATITUDE);
				location.setLongitude(U.LONGITUDE);
			}
		}
		return location;
	}
    
	/**
	 * Make an HTTP GET request to the given URL and return a JSONObject.
	 * @param url
	 * @return JSONObject
	 */
	public static JSONObject getObject(String rawJSON) {
		String contentString;
		JSONObject j = null;

		try {
			contentString = rawJSON;
			if (contentString != null) {
				j = new JSONObject(contentString);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return j;
	}


	public static JSONArray getArray(String rawJSON) {
		String contentString;
		JSONArray j = null;

		try {
			contentString = rawJSON;
			if (contentString != null) {
				j = new JSONArray(contentString);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return j;
	}
	
	/**
	 * Safely get integer value i o[key].
	 * 
	 * @param o
	 * @param key
	 * @return
	 */
	public static Integer gi(JSONObject o, String key) {
		Integer i;
		try {
			i = o.getInt(key);
		}
		catch (JSONException e) {
			i = 0;
		}
		return i;
	}

	/**
	 * Safely get integer value i o[key].
	 * 
	 * @param o
	 * @param key
	 * @return
	 */
	public static Double gd(JSONObject o, String key) {
		Double d;
		try {
			d = o.getDouble(key);
		}
		catch (JSONException e) {
			d = 0.0;
		}
		return d;
	}
	
	/**
	 * Safely get string value in o[key].
	 * 
	 * @param o
	 * @param key
	 * @return
	 */
	public static String gs(JSONObject o, String key) {
		String s;
		try {
			s = o.getString(key);
		}
		catch (JSONException e) {
			s = "";
		}
		return s;
	}

	/**
	 * Safely get boolean value in o[key].
	 * 
	 * @param o
	 * @param key
	 * @return
	 */
	public static Boolean gb(JSONObject o, String key) {
		Boolean b;
		try {
			b = o.getBoolean(key);
		}
		catch (JSONException e) {
			b = false;
		}
		return b;
	}
	/**
	 * Safely get JSONArray in o[key]
	 * 
	 * @param o
	 * @param key
	 * @return
	 */
	public static JSONArray ga(JSONObject o, String key) {
		JSONArray ja;
		try {
			ja = o.getJSONArray(key);
		}
		catch (Exception e) {
			ja = new JSONArray();
		}
		return ja;
	}
	
	
	
	/**
	 * Safely get JSONObject in o[key]
	 * 
	 * @param o
	 * @param key
	 * @return
	 */
	public static JSONObject go(JSONObject o, String key) {
		JSONObject jo;
		try {
			jo = o.getJSONObject(key);
		}
		catch (JSONException e) {
			jo = new JSONObject();
		}
		return jo;
	}
	
	public static void clearImage(ImageView v){
		v.setVisibility(View.GONE);
	}
	
	public static void setImage(ImageView v, int resid){
		if (v.getVisibility() != View.VISIBLE){
			v.setVisibility(View.VISIBLE);
		}
		v.setBackgroundResource(resid);
	}
	
	
	public static void doListMapSearch(){
		
	}
	  
	  public static String convertPriceString(String s){
			 String matchString = "\\<strong\\>(\\S+)\\<\\/strong\\>";
			 String capture ="\\$"+"$1";
			 Pattern pattern = Pattern.compile(matchString);
			 Matcher matcher = pattern.matcher(s);
			 return matcher.replaceAll(capture);
		 }
	  
	  public static String getTimeFromSeconds(Integer totalSeconds){
			String timeString = "";
			if (totalSeconds > 0) {
				Integer hours = totalSeconds / 3600;
				Integer minutes = (totalSeconds % 3600) / 60;
				Integer seconds = totalSeconds % 60;
				String sSeconds = seconds.toString();
				String sMinutes = minutes.toString();
			
				if (seconds < 10 && seconds > 0) {
					sSeconds = "0"+sSeconds;
				}
				if (minutes < 10 && minutes > 0) {
					sMinutes = "0"+sMinutes;
				}
				timeString = hours + ":" + sMinutes + ":" + sSeconds;
			} 
			return timeString;
		}
	  
	  public static int getDistnaceBetweenPoints(Location a, Location b){
			double distance = a.distanceTo(b);
			Double distanceMiles = distance * 0.00062137119;
			int intDistance = distanceMiles.intValue();
			return intDistance;
	  }
	  
	  public static double getDistnaceBetweenPointsDouble(Location a, Location b){
			double distance = a.distanceTo(b);
			Double distanceMiles = distance * 0.00062137119;
			BigDecimal bd = new BigDecimal(distanceMiles).setScale(1, RoundingMode.HALF_EVEN);
			distanceMiles = bd.doubleValue();
			return distanceMiles;
	  }

	public static Location getLocation(LatLng target) {
		Location retLocation = new Location("camera-location");
		retLocation.setLatitude(target.latitude);
		retLocation.setLongitude(target.longitude);
		
		return retLocation;
	}
}
