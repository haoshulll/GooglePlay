package com.myapp.googleplay.util;


import android.graphics.drawable.Drawable;

import com.myapp.googleplay.global.GooglePlayApplication;

public class CommonUtils {
	public static void runOnUIThread(Runnable runnable){
		GooglePlayApplication.getMainHandler().post(runnable);
	}

	/**
	 * 获得dp资源，获取后的值已经转换成px
	 * @param resId
	 * @return
	 */
	public static float getDimens(int resId){
		return GooglePlayApplication.getContext().getResources().getDimensionPixelSize(resId);
	}
	
	public static String getString(int resId){
		return GooglePlayApplication.getContext().getResources().getString(resId);
	}

	public static int getColor(int resId){
		return GooglePlayApplication.getContext().getResources().getColor(resId);
	}

	public static String[] getStringArray(int resId){
		return GooglePlayApplication.getContext().getResources().getStringArray(resId);
	}

	public static Drawable getDrawable(int resId){
		return GooglePlayApplication.getContext().getResources().getDrawable(resId);
	}
}
