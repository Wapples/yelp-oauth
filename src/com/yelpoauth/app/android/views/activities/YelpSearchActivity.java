package com.yelpoauth.app.android.views.activities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;
import android.widget.SearchView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yelpoauth.app.android.R;
import com.yelpoauth.app.android.activities.fragments.ListingListFragment;
import com.yelpoauth.app.android.adapters.ImageAdapter;
import com.yelpoauth.app.android.helpers.GoogleImageClient;
import com.yelpoauth.app.android.helpers.U;
import com.yelpoauth.app.android.models.Business;
import com.yelpoauth.app.android.models.BusinessFactory;
import com.yelpoauth.app.android.models.Image;
import com.yelpoauth.app.android.models.Query;
import com.yelpoauth.app.android.oauth.VolleyYelpClient;

public class YelpSearchActivity extends FragmentActivity {
	public ImageAdapter mAdapter;
	private GridView mGridView;
	private String mCurrentQuery;
	private GoogleImageClient mClient;
	private EndlessScrollListener mEndlessListener;
	private ArrayList<Image> mImageList;
	private YelpSearchActivity mContext;
	private static String IMAGE_LIST_TAG = "image-list";
	private FragmentManager fm;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);  
		setContentView(R.layout.search_parent_layout);

		mContext = this;
		fm = getSupportFragmentManager();
		searchFromHistoryActivity();
	}

	private void search(String query) {
		//reset state of scroll listener 
		mEndlessListener.resetListener();
		
		// Clear out previous search results
		mAdapter.clear();
		
		// set current search query
		mCurrentQuery = query;
		
		//log query in history
		Query.storeQuery(mCurrentQuery);
		
		//set title
		setTitle("Searching - " + mCurrentQuery);
	
		// load images
		loadMore();
	}
	
	private void searchYelp(String query){
		Location userLocation = U.getCurrentLocation(mContext);
		VolleyYelpClient.search(query,
								0,
								userLocation.getLatitude() +"", 
								userLocation.getLongitude() + "", 
								yelpResponseSuccessListener(), 
								yelpErrorListener());
		Query.storeQuery(query);
	}
	
	private void loadMore() {
		setProgressBarIndeterminateVisibility(true);
		
		//Use GoogleImageClient to make request
		mClient.makeSearchRequest(mCurrentQuery, mAdapter.getCount(),
											responseSuccessListener(), responseErrorListener());
	}
	
	
	private Response.Listener<JSONObject> responseSuccessListener() {	
		return new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				processResponse(response);
				setProgressBarIndeterminateVisibility(false);
			}
		};
	}
	

	private Response.ErrorListener responseErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				showErrorDialog(error);
			}
		};
	}
	
	private Response.Listener<JSONObject> yelpResponseSuccessListener() {	
		return new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				setProgressBarIndeterminateVisibility(false);
				
				List<Business> businessList = BusinessFactory.getBusinessList(response);
				Business.storeAll(businessList);
//				List<Business> storedBusinessList = Business.getAll();
				
				Bundle extras = new Bundle();
				extras.putSerializable("business-list", (Serializable) businessList);
				
				ListingListFragment businessListFrag = new ListingListFragment();
				businessListFrag.setArguments(extras);
				
				FragmentTransaction ft = fm.beginTransaction();
				ft.replace(R.id.content_frame, businessListFrag);
				ft.commit();
			}
		};
	}
	

	private Response.ErrorListener yelpErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				showErrorDialog(error);
			}
		};
	}

	private void showErrorDialog(Exception e) {
		e.printStackTrace();
	}
	
	protected void processResponse(JSONObject response) {	
		mImageList = Image.fromJSON(response);
		mAdapter.addAll(mImageList);
		mAdapter.notifyDataSetChanged();
	}
	
	private boolean searchFromHistoryActivity() {
		boolean ret = false;
		Bundle extras = null;
		if (getIntent().getExtras() != null) {
			ret = true;
			extras = getIntent().getExtras();
			
			String queryString = extras.getString("query");
			search(queryString);
		}
		return ret;
	}
	
	private void showHistory() {
		Intent i = new Intent(getApplicationContext(), QueryHistoryActivity.class);
		startActivity(i);
	}

	public ImageAdapter getCurrentAdapater(){
		ImageAdapter ret = null;
		if (mAdapter!= null){
			ret = mAdapter;
		}
		return ret;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_search, menu);

		final MenuItem searchItem = menu.findItem(R.id.action_search);
		final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		searchView.setSubmitButtonEnabled(true);
		
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String queryString) {
				//perform search
				//search(queryString);
				searchYelp(queryString);
				//collapse  search 
				searchItem.collapseActionView();

				return true;
			}

			@Override
			public boolean onQueryTextChange(String currentText) {
				return false;
			}
		});
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_search_history:
			showHistory();
			break;
		default:
			break;
		}
		return true;
	}
	
	public class EndlessScrollListener implements OnScrollListener {
		private int visibleThreshold = 4;
		private int currentPage = 0;
		private int previousTotal = 0;
		private boolean loading = true;

		public EndlessScrollListener() {
			
		}
		
		public void resetListener(){
			previousTotal = 0;
			loading = true;
		}
		
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			if (loading) {
				if (totalItemCount > previousTotal) {
					loading = false;
					previousTotal = totalItemCount;
					currentPage++;
				}
			}
			//if we are not loading and the (total - visible in view) <=  (current top item position + the threshold value)
			if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
				loadMore();
				loading = true;
			}
		}

		public int getCurrentPage() {
			return currentPage;
		}
	}
	
	@Override
	public void onResume(){
		super.onResume();
		if (mImageList != null){
			mAdapter.addAll(mImageList);
		}
	}
	 @Override
	 public void onSaveInstanceState(Bundle savedInstanceState) {
		 // Save custom values into the bundle
		 savedInstanceState.putSerializable(IMAGE_LIST_TAG, mImageList);
	  	 // Always call the superclass so it can save the view hierarchy state
	  	 super.onSaveInstanceState(savedInstanceState);
	  }
	 
	 public void onRestoreInstanceState(Bundle savedInstanceState) {
		    // Always call the superclass so it can restore the view hierarchy
		    super.onRestoreInstanceState(savedInstanceState);
		    // Restore state members from saved instance
		    mImageList = (ArrayList<Image>) savedInstanceState.getSerializable(IMAGE_LIST_TAG);
		}
}