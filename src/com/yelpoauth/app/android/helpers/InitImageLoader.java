package com.yelpoauth.app.android.helpers;

import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.HttpClientImageDownloader;
import com.yelpoauth.app.android.R;

public class InitImageLoader {
	
	public static void init(Context c){
		
//	 	ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
// 		.defaultDisplayImageOptions(defaultOptions)
// 		.build();

    	DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
 		.cacheInMemory()
 		.cacheOnDisc()
 		.showStubImage(R.drawable.yelp_placeholder)
 		.showImageOnFail(R.drawable.yelp_placeholder)
 		.build();
    	
		HttpParams params = new BasicHttpParams();
        // Turn off stale checking. Our connections break all the time anyway,
        // and it's not worth it to pay the penalty of checking every time.
        HttpConnectionParams.setStaleCheckingEnabled(params, false);
        // Default connection and socket timeout of 10 seconds. Tweak to taste.
        HttpConnectionParams.setConnectionTimeout(params, 10 * 1000);
        HttpConnectionParams.setSoTimeout(params, 10 * 1000);
        HttpConnectionParams.setSocketBufferSize(params, 8192);

        // Don't handle redirects -- return them to the caller. Our code
        // often wants to re-POST after a redirect, which we must do ourselves.
        HttpClientParams.setRedirecting(params, false);
        // Set the specified user agent and register standard protocols.
        HttpProtocolParams.setUserAgent(params, "android");
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

        ClientConnectionManager manager = new ThreadSafeClientConnManager(params, schemeRegistry);
        
        ImageLoaderConfiguration config =
                new ImageLoaderConfiguration
                        .Builder(c)
                        .defaultDisplayImageOptions(defaultOptions)
                        .threadPoolSize(1)
                        .memoryCache(new WeakMemoryCache())
                        .imageDownloader(new HttpClientImageDownloader(c,new DefaultHttpClient(manager, params)))
                        .build();
        
     	ImageLoader.getInstance().init(config);

	}

}
