package com.myapp.googleplay.ui.holder;

import android.text.format.Formatter;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.myapp.googleplay.R;
import com.myapp.googleplay.bean.AppInfo;
import com.myapp.googleplay.global.GooglePlayApplication;
import com.myapp.googleplay.global.ImageLoaderOptions;
import com.myapp.googleplay.http.Url;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by haoshul on 2016/6/22.
 */
public class HomeHolder extends BaseHolder<AppInfo> {

    @InjectView(R.id.iv_icon) ImageView ivIcon;
    @InjectView(R.id.tv_name) TextView tvName;
    @InjectView(R.id.rb_star) RatingBar rbStar;
    @InjectView(R.id.tv_size) TextView tvSize;
    @InjectView(R.id.tv_des) TextView tvDes;

    @Override
    protected View initView() {
        View holderView = View.inflate(GooglePlayApplication.getContext(), R.layout.adapter_home, null);
        ButterKnife.inject(this,holderView);
        return holderView;
    }

    private ArrayList<String> loadedImage = new ArrayList<>();

    @Override
    public void bindView(AppInfo data) {

        tvName.setText(data.getName());
        rbStar.setRating(data.getStars());
        tvSize.setText(Formatter.formatFileSize(GooglePlayApplication.getContext(),data.getSize()));
        tvDes.setText(data.getDes());

        if (loadedImage.contains(data.getIconUrl())){
            //加载时不需要动画
            ImageLoader.getInstance().displayImage(Url.IMAGE_PREFIX+data.getIconUrl(),ivIcon,ImageLoaderOptions.default_options);
        }else{
            //加载时需要动画
            ImageLoader.getInstance().displayImage(Url.IMAGE_PREFIX+data.getIconUrl(),ivIcon, ImageLoaderOptions.options);
        }




    }
}
