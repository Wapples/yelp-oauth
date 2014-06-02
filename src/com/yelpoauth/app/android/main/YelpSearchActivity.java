package com.yelpoauth.app.android.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.yelpoauth.app.android.R;
import com.yelpoauth.app.android.activities.fragments.ListingListFragment;
import com.yelpoauth.app.android.views.activities.QueryHistoryActivity;

public class YelpSearchActivity extends FragmentActivity {
	private FragmentManager fm;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);  
		setContentView(R.layout.search_parent_layout);
		
		fm = getSupportFragmentManager();
		if (getIntent().getExtras() != null) {
			searchFromHistoryActivity();
		} else {
			inflateSearch();
		}
	}	
	
	private void inflateSearch() {			
			ListingListFragment businessListFrag = new ListingListFragment();
			FragmentTransaction ft = fm.beginTransaction();
			ft.replace(R.id.content_frame, businessListFrag);
			ft.commit();
	}
	
	
	private void searchFromHistoryActivity() {
		ListingListFragment businessListFrag = new ListingListFragment();
		Bundle extras = getIntent().getExtras();
		businessListFrag.setArguments(extras);
		
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.content_frame, businessListFrag);
		ft.commit();
	}
	
	private void showHistory() {
		Intent i = new Intent(getApplicationContext(), QueryHistoryActivity.class);
		startActivity(i);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_search, menu);
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