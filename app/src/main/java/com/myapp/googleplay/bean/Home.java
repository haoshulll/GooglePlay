package com.myapp.googleplay.bean;

import com.myapp.googleplay.util.JsonUtil;

import java.util.ArrayList;

/**
 * 封装home页返回的json数据
 * @author Administrator
 *
 */
public class Home {
	
	public static Home fromJson(String json){
		return JsonUtil.parseJsonToBean(json, Home.class);
	}
	
	private ArrayList<String> picture;
	private ArrayList<AppInfo> list;
	public ArrayList<String> getPicture() {
		return picture;
	}
	public void setPicture(ArrayList<String> picture) {
		this.picture = picture;
	}
	public ArrayList<AppInfo> getList() {
		return list;
	}
	public void setList(ArrayList<AppInfo> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "Home [picture=" + picture + ", list=" + list + "]";
	}
	
	
}
