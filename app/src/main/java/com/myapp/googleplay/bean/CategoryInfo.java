package com.myapp.googleplay.bean;

public class CategoryInfo {
//	private String title;
	private String url1;
	private String url2;
	private String url3;
	private String name1;
	private String name2;
	private String name3;
	
//	public CategoryInfo(String title){
//		this.title = title;
//	}
	
	public String getUrl1() {
		return url1;
	}
	public void setUrl1(String url1) {
		this.url1 = url1;
	}
	public String getUrl2() {
		return url2;
	}
	public void setUrl2(String url2) {
		this.url2 = url2;
	}
	public String getUrl3() {
		return url3;
	}
	public void setUrl3(String url3) {
		this.url3 = url3;
	}
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getName2() {
		return name2;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
	public String getName3() {
		return name3;
	}
	public void setName3(String name3) {
		this.name3 = name3;
	}
//	public String getTitle() {
//		return title;
//	}
//	public void setTitle(String title) {
//		this.title = title;
//	}

//	@Override
//	public String toString() {
//		return "CategoryInfo [title=" + title + ", url1=" + url1 + ", name1="
//				+ name1 + "]";
//	}

	@Override
	public String toString() {
		return "CategoryInfo [ url1=" + url1 + ", name1="
				+ name1 + "]";
	}
	
	
}