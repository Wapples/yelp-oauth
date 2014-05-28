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

	public Boolean hasInternet(){
        boolean wifiEnabled = wifiManager.isWifiEnabled();
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (!wifiEnabled && !activeNetwork.isConnected()){
        	return false;
        }
        return true;
	}
}
