package com.myapp.googleplay.ui.holder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myapp.googleplay.R;
import com.myapp.googleplay.bean.CategoryInfo;
import com.myapp.googleplay.global.GooglePlayApplication;
import com.myapp.googleplay.global.ImageLoaderOptions;
import com.myapp.googleplay.http.Url;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by haoshul on 2016/6/23.
 */
public class CategoryInfoHolder extends BaseHolder<Object> {
    @InjectView(R.id.iv_image1)
    ImageView ivImage1;
    @InjectView(R.id.tv_title1)
    TextView tvTitle1;
    @InjectView(R.id.iv_image2)
    ImageView ivImage2;
    @InjectView(R.id.tv_title2)
    TextView tvTitle2;
    @InjectView(R.id.iv_image3)
    ImageView ivImage3;
    @InjectView(R.id.tv_title3)
    TextView tvTitle3;
    @InjectView(R.id.ll_info2)
    LinearLayout llInfo2;
    @InjectView(R.id.ll_info3)
    LinearLayout llInfo3;

    @Override
    protected View initView() {
        View view = View.inflate(GooglePlayApplication.getContext(), R.layout.adapter_category_info, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void bindView(Object data) {
        CategoryInfo categoryInfo = (CategoryInfo) data;
        tvTitle1.setText(categoryInfo.getName1());
        ImageLoader.getInstance().displayImage(Url.IMAGE_PREFIX + categoryInfo.getUrl1(), ivImage1, ImageLoaderOptions.round_options);

        //由于第2，3个可能没有，所以显示的时候需要判断
        if (!TextUtils.isEmpty(categoryInfo.getName2())){
            //由于convertView复用，需要重新设置为可见
            llInfo3.setVisibility(View.VISIBLE);
            tvTitle2.setText(categoryInfo.getName2());
            ImageLoader.getInstance().displayImage(Url.IMAGE_PREFIX + categoryInfo.getUrl2(), ivImage2, ImageLoaderOptions.round_options);
        }else {
            llInfo2.setVisibility(View.INVISIBLE);
        }


        if (!TextUtils.isEmpty(categoryInfo.getName3())){
            //由于convertView复用，需要重新设置为可见
            llInfo3.setVisibility(View.VISIBLE);
            tvTitle3.setText(categoryInfo.getName3());
            ImageLoader.getInstance().displayImage(Url.IMAGE_PREFIX + categoryInfo.getUrl3(), ivImage3, ImageLoaderOptions.round_options);
        }else {
            llInfo3.setVisibility(View.INVISIBLE);
        }

    }
}
