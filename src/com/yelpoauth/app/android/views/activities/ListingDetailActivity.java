package com.yelpoauth.app.android.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yelpoauth.app.android.R;
import com.yelpoauth.app.android.models.Business;

public class ListingDetailActivity extends FragmentActivity {

	public static final int MENU_MAP = 1;
	public static final int MENU_CATEGORIES = 2;
	public static final int MENU_BOOKMARKS = 3;
	public static final int MENU_SMOKING = 4;
	public static final int MENU_COUPONS = 5;
	public static final int MENU_MAINMENU = 6;
	private static final String TAG = "ListingDetailActivity";
	public static final int REQUEST_FOR_LOGIN = 1;

	private Activity mContext;

	private TextView tvTitleText;
	private TextView tvReviewCount;
	private TextView tvDistance;
	private TextView tvHours;

	private Location phoneLocation;
	private Location dispLocation;
	private FragmentManager fragmentManager;
	private TextView tvAddress;
	private GoogleMap mGoogleMap;
	private Button bDirections;
	private Button bCall;
	private TextView tvPhone;
	protected SharedPreferences mSettings;
	private boolean mDetailsInflated = false;
	private Business mListing;
	private Business mBusiness;
	private ImageView ivRating;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kk_listing_details_fragment);

		mContext = this;

		// TextViews
		tvTitleText = (TextView) findViewById(R.id.dispensary_title_map);
		tvReviewCount = (TextView) findViewById(R.id.dispensary_reviews_map);
		tvDistance = (TextView) findViewById(R.id.dispensary_distance_map);
		tvHours = (TextView) findViewById(R.id.dispensary_hours_map);
		tvAddress = (TextView) findViewById(R.id.dispensary_countdown_map);
		
		//Image Views
		ivRating = (ImageView) findViewById(R.id.iv_listing_rating);

		// Details Controls
		bDirections = (Button) findViewById(R.id.b_dispensary_directions_detail);
		bCall = (Button) findViewById(R.id.b_dispensary_phone_detail);

		if (getIntent().getExtras() != null) {
			inflateDetails(getIntent().getExtras());
		}
	}

	@Override
	public void onStart() {
		super.onStart();

		invalidateOptionsMenu();
	}

	public void inflateDetails(Bundle extras) {
		if (extras != null) {
			mBusiness = (Business) extras.getSerializable("business");
		}
		processListingResponse(mBusiness);
		mDetailsInflated = true;
	}

	public void processListingResponse(Business b) {
		int intDistance = (int) b.distance.doubleValue();
		String streetAddress = b.streetAddress + ", " + b.city + " " + b.state + " " + b.zip;
		
		tvTitleText.setText(b.name);
		tvTitleText.setSelected(true);
		tvReviewCount.setText(b.reviewCount + " reviews");
		tvDistance.setText(intDistance + " mi");
		// tvHours.setText("Hours: " + mListing.hoursOpen);
		tvAddress.setText(streetAddress);
		tvAddress.setSelected(true);
        
		ImageLoader.getInstance().displayImage(b.ratingImageUrl, ivRating);

		bCall.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String number = "tel:" + mListing.phone.trim();
				Intent phoneCallIntent = new Intent(Intent.ACTION_CALL, Uri
						.parse(number));
				mContext.startActivity(phoneCallIntent);
			}
		});

		bDirections.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Uri uri = Uri.parse("http://maps.google.com/maps?" + "saddr="
						+ Double.toString(phoneLocation.getLatitude()) + ","
						+ Double.toString(phoneLocation.getLongitude()) + "&"
						+ "daddr=" + dispLocation.getLatitude() + ","
						+ dispLocation.getLongitude());
				Intent directionsIntent = new Intent(Intent.ACTION_VIEW, uri);
				mContext.startActivity(directionsIntent);
			}
		});

	}


	@Override
	public void onStop() {
		super.onStop();

	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	public void loadMore(){
		
	}

}
