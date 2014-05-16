package com.yelpoauth.app.android.helpers;

import com.google.android.gms.maps.model.LatLng;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;

public class LocationHelper implements LocationListener {
	// The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    
	private static WifiManager wifiManager;

	private Context mContext;
	
	public LocationHelper(Context context){
		mContext = context;
	}

	public Location getLocation() {
	    	Location location = null;
			
	        wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE); 
	
	        try {
	        	LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
	
	            // getting GPS status
	            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	            
	            //is WiFi enabled?
	            boolean wifiEnabled = wifiManager.isWifiEnabled();
	            
	            ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
	            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
	           
	            // if DATA Enabled get lat/long using WIFI Services
	            if (activeNetwork.isConnected()) {
	                locationManager.requestLocationUpdates(
	                        LocationManager.NETWORK_PROVIDER,
	                        MIN_TIME_BW_UPDATES,
	                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
	                Log.d("Network", "Data enabled");
	                if (locationManager != null) {
	                    location = locationManager
	                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	
	                }
	            }
	            
	         // if WIFI Enabled get lat/long using WIFI Services
	            if (wifiEnabled) {
	                locationManager.requestLocationUpdates(
	                        LocationManager.NETWORK_PROVIDER,
	                        MIN_TIME_BW_UPDATES,
	                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
	                Log.d("Network", "Network enabled");
	                if (locationManager != null) {
	                    location = locationManager
	                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	
	                }
	            }
	            
	            // if GPS Enabled get lat/long using GPS Services
	            if (isGPSEnabled) {
	                if (location == null) {
	                    locationManager.requestLocationUpdates(
	                            LocationManager.GPS_PROVIDER,
	                            MIN_TIME_BW_UPDATES,
	                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
	                    Log.d("GPS Enabled", "GPS Enabled");
	                    if (locationManager != null) {
	                        location = locationManager
	                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
	                        if (location == null){
	                        	location = new Location("gps");
	                    		location.setLatitude(U.LATITUDE);
	                    		location.setLongitude(U.LONGITUDE);
	                        }
	                    }
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	
	        return location;
	    }

	public LatLng getLatLng(){
		Location location = null;
		
        wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE); 

        try {
        	LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

            // getting GPS status
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            
            //is WiFi enabled?
            boolean wifiEnabled = wifiManager.isWifiEnabled();
            
            ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
           
            // if DATA Enabled get lat/long using WIFI Services
            if (activeNetwork.isConnected()) {
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                Log.d("Network", "Data enabled");
                if (locationManager != null) {
                    location = locationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                }
            }
            
         // if WIFI Enabled get lat/long using WIFI Services
            if (wifiEnabled) {
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                Log.d("Network", "Network enabled");
                if (locationManager != null) {
                    location = locationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                }
            }
            
            // if GPS Enabled get lat/long using GPS Services
            if (isGPSEnabled) {
                if (location == null) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("GPS Enabled", "GPS Enabled");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location == null){
                        	location = new Location("gps");
                    		location.setLatitude(U.LATITUDE);
                    		location.setLongitude(U.LONGITUDE);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return convertToLatLng(location);	
	}
	
	private LatLng convertToLatLng(Location location) {
		return new LatLng(location.getLatitude(), location.getLongitude());
	}

	@Override
	public void onLocationChanged(Location location) {
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		
	}
}

