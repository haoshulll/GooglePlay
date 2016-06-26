package com.myapp.googleplay.ui.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.myapp.googleplay.global.GooglePlayApplication;
import com.myapp.googleplay.global.ImageLoaderOptions;
import com.myapp.googleplay.http.Url;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by haoshul on 2016/6/23.
 */
public class HomeHeaderAdapter extends BasePagerAdapter<String> {

    public HomeHeaderAdapter(ArrayList<String> urlList) {
        super(urlList);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = null;
        if (imageView == null){
            imageView = new ImageView(GooglePlayApplication.getContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
            ImageLoader.getInstance().displayImage(Url.IMAGE_PREFIX+list.get(position),imageView, ImageLoaderOptions.options);

            ViewHelper.setScaleX(imageView,0.5f);
            ViewHelper.setScaleY(imageView,0.5f);
            ViewPropertyAnimator.animate(imageView)
                    .rotationY(360)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(700)
                    .start();
            container.addView(imageView);
        }

        return imageView;
    }

}
