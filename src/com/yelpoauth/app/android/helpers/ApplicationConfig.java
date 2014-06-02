package com.yelpoauth.app.android.helpers;

import android.app.ActionBar;
import android.app.Activity;
import android.view.View;

import com.yelpoauth.app.android.R;


public class ApplicationConfig {

    public static void setupActionBar(Activity activity) {

	View actionBarTheme = activity.getLayoutInflater().inflate(
		R.layout.custom_action_bar, null);

	// enable ActionBar app icon to behave as action to toggle nav drawer
	ActionBar actionBar = activity.getActionBar();
	actionBar.setDisplayShowCustomEnabled(true);
	// actionBar.setDisplayHomeAsUpEnabled(true);
	// actionBar.setHomeButtonEnabled(true);
	actionBar.setDisplayShowTitleEnabled(false);
	actionBar.setIcon(android.R.color.transparent);
	actionBar.setCustomView(actionBarTheme);
    }
}
