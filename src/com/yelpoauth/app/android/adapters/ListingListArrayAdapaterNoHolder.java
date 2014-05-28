package com.yelpoauth.app.android.adapters;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yelpoauth.app.android.R;
import com.yelpoauth.app.android.models.Business;
import com.yelpoauth.app.android.views.activities.ListingDetailActivity;

public class ListingListArrayAdapaterNoHolder extends ArrayAdapter<Business> {
	public Location mUserLocation;

	public ListingListArrayAdapaterNoHolder(Context context, List<Business> listings) {
		super(context, R.layout.kk_disp_list_view, listings);

	}
	
	public ListingListArrayAdapaterNoHolder(Context context) {
		super(context, 0);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Context context = getContext();
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.kk_disp_list_view, null);	
		}
		
		TextView addressView = (TextView) convertView
				.findViewById(R.id.tv_dispensary_address_list);
		TextView distanceView = (TextView) convertView
				.findViewById(R.id.dispensary_distance_list);
		TextView hoursView = (TextView) convertView
				.findViewById(R.id.dispensary_hours_list);
		TextView reviewCountView = (TextView) convertView
				.findViewById(R.id.dispensary_reviews_list);
		TextView titleView = (TextView) convertView
				.findViewById(R.id.dispensary_title_list);
		ImageView avatarView = (ImageView) convertView
				.findViewById(R.id.dispensary_avatar_list);
		ImageView ratingImageView = (ImageView) convertView
				.findViewById(R.id.iv_rating_list);
		Button callView = (Button) convertView
				.findViewById(R.id.dispensary_phone_list);
		Button directionsView = (Button) convertView
				.findViewById(R.id.dispensary_directions_list);
		Business business = getItem(position);
		int distance = (int) business.distance.doubleValue();

		addressView.setText(business.streetAddress);
		distanceView.setText(distance + " mi");
		// viewHolder.hoursView.setText(listing.hoursOpen + " - "
		// + listing.hoursClose);
		reviewCountView.setText(business.reviewCount + "");
		titleView.setText(business.name);
		callView.setOnClickListener(new CallClickListener(business,
				context));
		convertView.setOnClickListener(new ListItemClickListener(business,
				context));

		ImageLoader.getInstance().displayImage(business.imageUrl, avatarView);
		ImageLoader.getInstance().displayImage(business.ratingImageUrl, ratingImageView);
		
		return convertView;
	}

	private class CallClickListener implements OnClickListener {
		private Business mBusiness;
		private Context mContext;

		public CallClickListener(Business b, Context c) {
			mBusiness = b;
			mContext = c;
		}

		@Override
		public void onClick(View v) {
			String dispPhone = mBusiness.phone;
			String number = "tel:" + dispPhone;
			Intent phoneCallIntent = new Intent(Intent.ACTION_CALL,
					Uri.parse(number));
			mContext.startActivity(phoneCallIntent);
		}
	}

	private class DirectionsClickListener implements OnClickListener {
		private Business mBusiness;
		private Context mContext;
		private Location mListingLocation;

		public DirectionsClickListener(Business l, Context c, Location location) {
			mBusiness = l;
			mContext = c;
			mListingLocation = location;
		}

		@Override
		public void onClick(View v) {
			Uri uri = Uri.parse("http://maps.google.com/maps?" + "saddr="
					+ mUserLocation.getLatitude() + ","
					+ mUserLocation.getLongitude() + "&" + "daddr="
					+ mListingLocation.getLatitude() + ","
					+ mListingLocation.getLongitude());
			Intent directionsIntent = new Intent(Intent.ACTION_VIEW, uri);
			mContext.startActivity(directionsIntent);
		}
	}

	private class ListItemClickListener implements OnClickListener {
		private Business mBusiness;
		private Context mContext;

		public ListItemClickListener(Business b, Context c) {
			mBusiness = b;
			mContext = c;
		}

		@Override
		public void onClick(View v) {
			Bundle extras = new Bundle();
			Intent i = new Intent(mContext, ListingDetailActivity.class);
			extras.putSerializable("business", mBusiness);
			i.putExtras(extras);
			mContext.startActivity(i);
		}
	}
}
