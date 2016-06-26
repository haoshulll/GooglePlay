package com.myapp.googleplay.http;

import android.util.Log;

import com.myapp.googleplay.global.GooglePlayApplication;
import com.myapp.googleplay.util.LogUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class HttpHelper {
	private static String TAG = HttpHelper.class.getSimpleName();
	private static HttpClient httpClient = new DefaultHttpClient();

	/**
	 * get请求获取服务器返回数据
	 * @param url
	 * @return
	 */
	public static String get(String url){
		HttpClient httpClient = new DefaultHttpClient();
		Log.i(TAG, "get:request url: " + url);
		String result = null;
		HttpGet httpGet = new HttpGet(url);
		
		boolean retry = true;
		while(retry){
			try {
				HttpResponse httpResponse = httpClient.execute(httpGet);
				if(httpResponse.getStatusLine().getStatusCode()==200){
					InputStream is = httpResponse.getEntity().getContent();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] buffer = new byte[1024	];
					int len = -1;
					while((len=is.read(buffer))!=-1){
						baos.write(buffer,0,len);
						baos.flush();
					}
					result = new String(baos.toByteArray(),"UTF-8");

					is.close();
					baos.close();
					httpClient.getConnectionManager().closeExpiredConnections();
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				LogUtil.e("HttpHelp", e.getMessage());
				retry = false;
			}
		}

		Log.d(TAG, "get() returned: " + result);
		return result;
	}

	/**
	 * 下载文件，返回流对象
	 *
	 * @param url
	 * @return
	 */
	public static HttpResult download(String url) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		boolean retry = true;
		while (retry) {
			try {
				HttpResponse httpResponse = httpClient.execute(httpGet);
				if(httpResponse!=null){
					return new HttpResult(httpClient, httpGet, httpResponse);
				}
			} catch (Exception e) {
				retry = false;
				e.printStackTrace();
				LogUtil.e(GooglePlayApplication.TAG, "download: "+e.getMessage());
			}
		}
		return null;
	}

	/**
	 * Http返回结果的进一步封装
	 * @author Administrator
	 *
	 */
	public static class HttpResult {
		private HttpClient httpClient;
		private HttpGet httpGet;
		private HttpResponse httpResponse;
		private InputStream inputStream;

		public HttpResult(HttpClient httpClient, HttpGet httpGet,
						  HttpResponse httpResponse) {
			super();
			this.httpClient = httpClient;
			this.httpGet = httpGet;
			this.httpResponse = httpResponse;


		}

		/**
		 * 获取状态码
		 * @return
		 */
		public int getStatusCode() {
			StatusLine status = httpResponse.getStatusLine();
			return status.getStatusCode();
		}

		/**
		 * 获取输入流
		 * @return
		 */
		public InputStream getInputStream(){
			if(inputStream==null && getStatusCode()<300){
				HttpEntity entity = httpResponse.getEntity();
				try {
					inputStream =  entity.getContent();
				} catch (Exception e) {
					e.printStackTrace();
					LogUtil.e(this, "getInputStream: "+e.getMessage());
				}
			}
			return inputStream;
		}

		/**
		 * 关闭链接和流对象
		 */
		public void close() {
			if (httpGet != null) {
				httpGet.abort();
			}
			if(inputStream!=null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
					LogUtil.e(this, "close: "+e.getMessage());
				}
			}
			//关闭链接
			if (httpClient != null) {
				httpClient.getConnectionManager().closeExpiredConnections();
			}
		}
	}


}
