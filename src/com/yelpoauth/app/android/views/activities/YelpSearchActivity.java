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
	
	private void searchYelp(String query){
		Bundle extras = new Bundle();
		extras.putString("query", query);
		
		ListingListFragment businessListFrag = new ListingListFragment();
		businessListFrag.setArguments(extras);
		
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.content_frame, businessListFrag);
		ft.commit();
		
		Query.storeQuery(query);
	}
	
	protected void processResponse(JSONObject response) {	
		mImageList = Image.fromJSON(response);
		mAdapter.addAll(mImageList);
		mAdapter.notifyDataSetChanged();
	}
	
	private boolean searchFromHistoryActivity() {
		boolean ret = false;
//		Bundle extras = null;
//		if (getIntent().getExtras() != null) {
//			ret = true;
//			extras = getIntent().getExtras();
//			
//			String queryString = extras.getString("query");
//			search(queryString);
//		}
		return ret;
	}
	
	private void showHistory() {
		Intent i = new Intent(getApplicationContext(), QueryHistoryActivity.class);
		startActivity(i);
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
	
	
	
	@Override
	public void onResume(){
		super.onResume();
	}
	 @Override
	 public void onSaveInstanceState(Bundle savedInstanceState) {
	  	 // Always call the superclass so it can save the view hierarchy state
	  	 super.onSaveInstanceState(savedInstanceState);
	  }
	 
	 public void onRestoreInstanceState(Bundle savedInstanceState) {
		    // Always call the superclass so it can restore the view hierarchy
		    super.onRestoreInstanceState(savedInstanceState);
	}
}