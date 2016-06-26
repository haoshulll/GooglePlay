package com.myapp.googleplay.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static String formatToday(){
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}
}
