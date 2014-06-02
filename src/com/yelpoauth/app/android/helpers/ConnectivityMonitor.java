package com.yelpoauth.app.android.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

public class ConnectivityMonitor {
	private static WifiManager wifiManager;
	private Context mContext;
	
	public ConnectivityMonitor(Context c){
		mContext = c;
	}

	public boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}
