package com.myapp.googleplay.ui.adapter;

import android.view.ViewGroup;

import com.myapp.googleplay.global.GooglePlayApplication;
import com.myapp.googleplay.global.ImageLoaderOptions;
import com.myapp.googleplay.http.Url;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by haoshul on 2016/6/26.
 */
public class ImageScaleAdapter extends BasePagerAdapter<String> {
    public ImageScaleAdapter(ArrayList<String> list) {
        super(list);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(GooglePlayApplication.getContext());

//        ImageView imageView = new ImageView(GooglePlayApplication.getContext());
        ImageLoader.getInstance().displayImage(Url.IMAGE_PREFIX+list.get(position),photoView, ImageLoaderOptions.options);
        container.addView(photoView);
        return photoView;
    }
}
