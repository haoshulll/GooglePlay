package com.myapp.googleplay.manager;

import com.myapp.googleplay.bean.AppInfo;

public class DownloadInfo {
	private int id;//下载任务的唯一标识,在这里采用appInfo的id字段
	private long size;//总长度
	private long currentLength;//当前已经下载的长度
	private int state;//下载状态
	private String downloadUrl;//下载地址
	private String path;//保存路径

	public static DownloadInfo create(AppInfo appInfo){
		DownloadInfo downloadInfo = new DownloadInfo();
		downloadInfo.setId(appInfo.getId());
		downloadInfo.setSize(appInfo.getSize());
		downloadInfo.setDownloadUrl(appInfo.getDownloadUrl());
		downloadInfo.setCurrentLength(0);

		downloadInfo.setState(DownLoadManager.STATE_NONE);
		downloadInfo.setPath(DownLoadManager.DOWNLOAD_DIR+"/"+appInfo.getName()+".apk");

		return downloadInfo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getCurrentLength() {
		return currentLength;
	}

	public void setCurrentLength(long currentLength) {
		this.currentLength = currentLength;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}