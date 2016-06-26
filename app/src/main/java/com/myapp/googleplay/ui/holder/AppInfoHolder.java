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

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AppInfoHolder extends BaseHolder<AppInfo> {

    @InjectView(R.id.iv_icon)
    ImageView ivIcon;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.rb_star)
    RatingBar rbStar;
    @InjectView(R.id.tv_download_num)
    TextView tvDownloadNum;
    @InjectView(R.id.tv_version)
    TextView tvVersion;
    @InjectView(R.id.tv_date)
    TextView tvDate;
    @InjectView(R.id.tv_size)
    TextView tvSize;

    @Override
    public View initView() {
        View view = View.inflate(GooglePlayApplication.getContext(), R.layout.layout_detail_app_info, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void bindView(AppInfo appInfo) {
        ImageLoader.getInstance().displayImage(Url.IMAGE_PREFIX + appInfo.getIconUrl(), ivIcon, ImageLoaderOptions.round_options);
        ViewHelper.setTranslationY(ivIcon,-ivIcon.getHeight());
        ViewPropertyAnimator.animate(ivIcon)
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(5))
                .setDuration(350)
                .start();
        tvName.setText(appInfo.getName());
        rbStar.setRating(appInfo.getStars());
        tvDownloadNum.setText("下载：" + appInfo.getDownloadNum());
        tvVersion.setText("版本：" + appInfo.getVersion());
        tvDate.setText("日期：" + appInfo.getDate());
        tvSize.setText("大小：" + Formatter.formatFileSize(GooglePlayApplication.getContext(), appInfo.getSize()));
    }

}
