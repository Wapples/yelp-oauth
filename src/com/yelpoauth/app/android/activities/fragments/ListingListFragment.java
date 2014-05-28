/**
 * 
 */
package com.yelpoauth.app.android.activities.fragments;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yelpoauth.app.android.R;
import com.yelpoauth.app.android.adapters.ListingListArrayAdapater;
import com.yelpoauth.app.android.helpers.U;
import com.yelpoauth.app.android.models.Business;
import com.yelpoauth.app.android.models.BusinessFactory;
import com.yelpoauth.app.android.oauth.VolleyYelpClient;

/**
 * @author wapples
 * 
 */
public class ListingListFragment extends ListFragment {

	private static String TAG = "ListingListFragment";

	private Activity mActivity;
	private Bundle extras;
	private FragmentManager fm;
	private List<Business> mListingList;
	private ListingListArrayAdapater mListAdapter;
	private Location mUserLocation;
	private ProgressDialog progressDialog;

	private TextView mSearchResultView;
	private TextView mListfilterDistance;
	private TextView mListfilterRating;
	private TextView mListfilterReviews;
	private ImageView mMapToggleView;

	private String mQuery;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		View v = inflater
				.inflate(R.layout.fragment_list_view, container, false);

		mActivity = getActivity();

		progressDialog = new ProgressDialog(mActivity);
		progressDialog.setMessage("Loading Markers, Please Wait...");
		progressDialog.setCancelable(true);

		extras = getArguments();
		String queryString = extras.getString("query");
		
		List<Business> mData = (List<Business>) extras
				.getSerializable("business-list");

		mSearchResultView = (TextView) v.findViewById(R.id.search_results);

		mListfilterDistance = (TextView) v
				.findViewById(R.id.tv_list_filters_distance);
		mListfilterRating = (TextView) v
				.findViewById(R.id.tv_list_filters_rating);
		mListfilterReviews = (TextView) v
				.findViewById(R.id.tv_list_filters_reviews);

		// get Fragment Manager
		fm = getFragmentManager();

		setList(mData);

		return v;
	}

	@Override
	public void onStart() {
		super.onStart();

	}

	@Override
	public void onResume() {
		super.onResume();

	}

	public void sortListDistance() {
		sortByDistance(mListingList);

	}

	public void setList(List<Business> list) {

		mListingList = list;
		// Sort By Featured Status and Distance descending
		sortByDistance(mListingList);

		mSearchResultView.setText(mListingList.size() + "");

		// Create row list adapter
		mListAdapter = new ListingListArrayAdapater(getActivity(), mListingList);

		// Handler for distance filter
		mListfilterDistance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sortByDistance(mListingList);

				refreshList(mListingList);
			}
		});

		// Handler for distance filter
		mListfilterRating.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sortByRating(mListingList);

				refreshList(mListingList);
			}
		});

		// Handler for distance filter
		mListfilterReviews.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sortByReviews(mListingList);

				refreshList(mListingList);
			}
		});

		setListAdapter(mListAdapter);
	}

	public void sortByDistance(List<Business> listingList) {
		// Comparator by distance
		Comparator<Business> distanceSort = new Comparator<Business>() {
			public int compare(Business listing1, Business listing2) {
				Double distance1 = listing1.distance;
				Double distance2 = listing2.distance;

				return distance1.compareTo(distance2);
			}
		};
		Collections.sort(listingList, distanceSort);
	}

	public void sortByRating(List<Business> list) {
		// Comparator by rating
		Comparator<Business> ratingSort = new Comparator<Business>() {
			public int compare(Business obj1, Business obj2) {
				return obj1.rating.compareTo(obj2.rating);
			}
		};

		Collections.sort(list, ratingSort);
	}

	public void sortByReviews(List<Business> list) {
		// Comparator by review count
		Comparator<Business> revewSort = new Comparator<Business>() {
			public int compare(Business obj1, Business obj2) {
				return obj1.reviewCount.compareTo(obj2.reviewCount);
			}
		};

		Collections.sort(list, revewSort);
	}

	private void refreshList(List<Business> list) {
		mListAdapter = new ListingListArrayAdapater(getActivity(), list);
		mListAdapter.notifyDataSetChanged();
		getListView().invalidateViews();
	}
	
	public class EndlessScrollListener implements OnScrollListener {
		private int visibleThreshold = 10;
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

	public void loadMore() {
		Location userLocation = U.getCurrentLocation(mActivity);
		VolleyYelpClient.search(mQuery,
								mListAdapter.getCount(),
								userLocation.getLatitude() +"", 
								userLocation.getLongitude() + "", 
								yelpResponseSuccessListener(), 
								yelpErrorListener());
	}
	
	public void search(String query) {
		mQuery = query;
		Location userLocation = U.getCurrentLocation(mActivity);
		VolleyYelpClient.search(mQuery, 
								mListAdapter.getCount(),
								userLocation.getLatitude() +"", 
								userLocation.getLongitude() + "", 
								yelpResponseSuccessListener(), 
								yelpErrorListener());
	}
	
	private Response.Listener<JSONObject> yelpResponseSuccessListener() {	
		return new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				
				List<Business> businessList = BusinessFactory.getBusinessList(response);
				Business.storeAll(businessList);
//				List<Business> storedBusinessList = Business.getAll();
				
				setList(businessList);
				
//				Bundle extras = new Bundle();
//				extras.putSerializable("business-list", (Serializable) businessList);
//				
//				ListingListFragment businessListFrag = new ListingListFragment();
//				businessListFrag.setArguments(extras);
//				
//				FragmentTransaction ft = fm.beginTransaction();
//				ft.replace(R.id.content_frame, businessListFrag);
//				ft.commit();
			}
		};
	}
	

	private Response.ErrorListener yelpErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
			}
		};
	}
}
