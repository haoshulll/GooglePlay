package com.myapp.googleplay.ui.holder;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.myapp.googleplay.R;
import com.myapp.googleplay.bean.AppInfo;
import com.myapp.googleplay.global.GooglePlayApplication;
import com.myapp.googleplay.global.ImageLoaderOptions;
import com.myapp.googleplay.http.Url;
import com.myapp.googleplay.ui.activity.ImageScaleActivity;
import com.myapp.googleplay.util.CommonUtils;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by haoshul on 2016/6/25.
 */
public class AppScreenHolder extends BaseHolder<AppInfo> {
    @InjectView(R.id.ll_container)
    LinearLayout llContainer;

    @Override
    protected View initView() {
        View view = View.inflate(GooglePlayApplication.getContext(), R.layout.layout_detail_app_screen, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void bindView(AppInfo data) {
        int width = (int) CommonUtils.getDimens(R.dimen.dp_120);
        int height = (int) CommonUtils.getDimens(R.dimen.dp_200);
        int margin = (int) CommonUtils.getDimens(R.dimen.dp_8);
        final ArrayList<String> urlList = data.getScreen();
        for (int i = 0; i < urlList.size();i++){
            ImageView imageView = new ImageView(GooglePlayApplication.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,height);
            if (i>0){
                params.leftMargin = margin;
            }
            imageView.setLayoutParams(params);
            ViewPropertyAnimator.animate(imageView)
                    .rotationY(360)
                    .setDuration(350)
                    .start();

            //显示图片
            ImageLoader.getInstance().displayImage(Url.IMAGE_PREFIX+urlList.get(i),imageView, ImageLoaderOptions.options);
            //添加imageView到llContainer
            llContainer.addView(imageView);
            final int temp = i;

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GooglePlayApplication.getContext(), ImageScaleActivity.class);
                    intent.putStringArrayListExtra("urlList",urlList);
                    intent.putExtra("currentItem",temp);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    GooglePlayApplication.getContext().startActivity(intent);
                }
            });
        }
    }
}
