package com.myapp.googleplay.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class GooglePlayApplication extends Application{
	public static String TAG = "TAG";
	private static Context context;
	private static Handler mHandler;
	@Override
	public void onCreate() {
		super.onCreate();
		context = this;
		mHandler = new Handler();

		//初始化ImageLoader
		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
	}
	
	public static Handler getMainHandler(){
		return mHandler;
	}

	public static Context getContext(){
		return context;
	}

}
