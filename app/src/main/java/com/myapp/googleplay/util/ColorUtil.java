package com.myapp.googleplay.util;

import android.graphics.Color;

import java.util.Random;

public class ColorUtil {
	public static int randomColor(){
		Random random = new Random();
		
		int red = 30+random.nextInt(200);
		int green = 30+random.nextInt(200);
		int blue = 30+random.nextInt(200);
		return Color.rgb(red, green, blue);
	}
}
