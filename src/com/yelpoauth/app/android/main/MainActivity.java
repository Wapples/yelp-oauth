package com.yelpoauth.app.android.main;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;
import com.yelpoauth.app.android.R;
import com.yelpoauth.app.android.controllers.QueryController;
import com.yelpoauth.app.android.helpers.LocationHelper;
import com.yelpoauth.app.android.helpers.U;
import com.yelpoauth.app.android.oauth.Yelp;
import com.yelpoauth.app.android.oauth.YelpCredentials;

import android.app.Activity;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
	
	private static String TAG = "Main";
	private Yelp yelpService;
	private LocationHelper locationHelper;
	private GetSearchResults searchTask;
	private MainActivity mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_acitivty);
		
		mContext = this;
		locationHelper = new LocationHelper(mContext);
		yelpService = new Yelp(YelpCredentials.CONSUMER_KEY, 
									YelpCredentials.CONSUMER_SECRET, 
									YelpCredentials.TOKEN, 
									YelpCredentials.TOKEN_SECRET);
		
		new GetSearchResults().execute();
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onStop() {
		searchTask.cancel(true);
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	public class GetSearchResults extends AsyncTask<String, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(String... params) {
			String searchString = "";
			if (params.length > 0){
				searchString = params[0];
				QueryController.storeQuery(searchString);
			}
			Location location = U.getCurrentLocation(mContext);
			LatLng myLocation = locationHelper.convertToLatLng(location);
			String response = yelpService.search(searchString , myLocation.latitude, myLocation.longitude);
			JSONObject responseObject = U.getObject(response);
			return responseObject;
		}
				
		@Override
		protected void onPostExecute(JSONObject response) {
			processResponse(response);
		}

		private void processResponse(JSONObject response) {
			JSONArray businessArray = U.ga(response, "businesses");
			Log.i(TAG, "response: " + response);
			Log.i(TAG, "Response Count: " + businessArray.length());

		}
		
	}
}
