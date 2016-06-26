package com.myapp.googleplay.http;

public interface Url {
	//主机名
	String SERVER_HOST = "http://127.0.0.1:8090/";
	String IMAGE_PREFIX = SERVER_HOST +"image?name=";
	//首页接口url
	String HOME = SERVER_HOST + "home?index=";
	//app页接口url
	String APP = SERVER_HOST + "app?index=";
	String GAME = SERVER_HOST + "game?index=";
	String SUBJECT = SERVER_HOST + "subject?index=";
	String CATEGORY = SERVER_HOST + "category?index=0";
	String RECOMMEND = SERVER_HOST + "recommend?index=0";
	String HOT = SERVER_HOST + "hot?index=0";
	String APP_DETAIL = SERVER_HOST + "detail?packageName=%1$s";
	//下载app接口url
	String APP_DOWNLOAD = SERVER_HOST + "download?name=%1$s";
	String APP_BREAK_DOWNLOAD = SERVER_HOST + "download?name=%1$s&range=%2$d";
}
