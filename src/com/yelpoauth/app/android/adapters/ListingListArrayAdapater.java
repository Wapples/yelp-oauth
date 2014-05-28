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

import com.squareup.picasso.Picasso;
import com.yelpoauth.app.android.R;
import com.yelpoauth.app.android.models.Business;
import com.yelpoauth.app.android.views.activities.ListingDetailActivity;

public class ListingListArrayAdapater extends ArrayAdapter<Business> {

	private static class ViewHolder {
		TextView addressView;
		TextView titleView;
		TextView hoursView;
		TextView reviewCountView;
		TextView distanceView;
		ImageView avatarView;
		ImageView ratingImageView;
		Button callView;
		Button directionsView;
	}

	public Location mUserLocation;

	public ListingListArrayAdapater(Context context, List<Business> listings) {
		super(context, R.layout.kk_disp_list_view, listings);

	}
	
	public ListingListArrayAdapater(Context context) {
		super(context, 0);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get the data item for this position
		// Check if an existing view is being reused, otherwise inflate the view
		ViewHolder viewHolder; // view lookup cache stored in tag
		Context context = getContext();
		if (convertView == null) {
			viewHolder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.kk_disp_list_view, null);
			viewHolder.addressView = (TextView) convertView
					.findViewById(R.id.tv_dispensary_address_list);
			viewHolder.distanceView = (TextView) convertView
					.findViewById(R.id.dispensary_distance_list);
			viewHolder.hoursView = (TextView) convertView
					.findViewById(R.id.dispensary_hours_list);
			viewHolder.reviewCountView = (TextView) convertView
					.findViewById(R.id.dispensary_reviews_list);
			viewHolder.titleView = (TextView) convertView
					.findViewById(R.id.dispensary_title_list);
			viewHolder.avatarView = (ImageView) convertView
					.findViewById(R.id.dispensary_avatar_list);
			viewHolder.ratingImageView = (ImageView) convertView
					.findViewById(R.id.iv_rating_list);
			viewHolder.callView = (Button) convertView
					.findViewById(R.id.dispensary_phone_list);
			viewHolder.directionsView = (Button) convertView
					.findViewById(R.id.dispensary_directions_list);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Business business = getItem(position);
		int distance = (int) business.distance.doubleValue();

		viewHolder.addressView.setText(business.streetAddress);
		viewHolder.distanceView.setText(distance + " mi");
		// viewHolder.hoursView.setText(listing.hoursOpen + " - "
		// + listing.hoursClose);
		viewHolder.reviewCountView.setText(business.reviewCount + "");
		viewHolder.titleView.setText(business.name);
		viewHolder.callView.setOnClickListener(new CallClickListener(business,
				context));
		convertView.setOnClickListener(new ListItemClickListener(business,
				context));

		// replace with image loader
		Picasso.with(getContext()).load(business.imageUrl).centerCrop()
				.resize(256, 256).placeholder(R.drawable.placeholder)
				.error(R.drawable.error).into(viewHolder.avatarView);

		Picasso.with(getContext()).load(business.ratingImageUrl).centerCrop()
				.resize(256, 256).placeholder(R.drawable.placeholder)
				.error(R.drawable.error).into(viewHolder.ratingImageView);

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
