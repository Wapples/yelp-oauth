package com.yelpoauth.app.android.adapters;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yelpoauth.app.android.R;
import com.yelpoauth.app.android.models.Image;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

 public class ImageAdapter extends ArrayAdapter<Image> {

		public ImageAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
        	
            if (convertView == null){
            	  LayoutInflater inflater = LayoutInflater.from(getContext());
                  convertView = inflater.inflate(R.layout.google_image, null);
            }
            // find the image view
            final ImageView iv = (ImageView) convertView.findViewById(R.id.image);
            
    		ImageLoader.getInstance().displayImage(getItem(position).url, iv);

            return convertView;
        }
    }