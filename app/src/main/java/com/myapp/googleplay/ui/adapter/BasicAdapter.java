package com.myapp.googleplay.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.BaseAdapter;

import com.myapp.googleplay.ui.holder.BaseHolder;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;

public abstract class BasicAdapter<T> extends BaseAdapter{
//	protected Context context;
	protected ArrayList<T> list;
	
	public BasicAdapter( ArrayList<T> list) {
//		this.context = context;
		this.list = list;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}
	@Override
	public Object getItem(int position) {
		return list.get(position);
	}
	@Override
	public long getItemId(int position) {
		return 0;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BaseHolder<Object> holder = null;
		if (convertView == null){
			holder = (BaseHolder<Object>) getHolder(position);
		}else{
			holder = (BaseHolder) convertView.getTag();
		}
		holder.bindView(list.get(position));

		//添加动画
		View holderView = holder.getHolderView();
		ViewHelper.setScaleX(holderView,0.5f);
		ViewHelper.setScaleY(holderView,0.5f);
		ViewPropertyAnimator.animate(holderView).scaleX(1f)
				.setInterpolator(new OvershootInterpolator())
				.setDuration(400)
				.start();
		ViewPropertyAnimator.animate(holderView).scaleY(1f)
				.setInterpolator(new OvershootInterpolator())
				.setDuration(400)
				.start();

		return holderView;
	}

	public abstract BaseHolder<T> getHolder(int position);
	
//	protected ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
//	protected static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
//
//		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
//
//		@Override
//		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//			if (loadedImage != null) {
//				ImageView imageView = (ImageView) view;
//				boolean firstDisplay = !displayedImages.contains(imageUri);
//				if (firstDisplay) {
//					FadeInBitmapDisplayer.animate(imageView, 500);
//					displayedImages.add(imageUri);
//				}
//			}
//		}
//	}
}
