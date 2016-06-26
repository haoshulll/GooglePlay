package com.myapp.googleplay.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.myapp.googleplay.R;
import com.myapp.googleplay.bean.Subject;
import com.myapp.googleplay.global.GooglePlayApplication;
import com.myapp.googleplay.global.ImageLoaderOptions;
import com.myapp.googleplay.http.Url;
import com.myapp.googleplay.ui.view.RatioImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by haoshul on 2016/6/23.
 */
public class SubjectHolder extends BaseHolder<Subject> {

    @InjectView(R.id.image) RatioImageView image;
    @InjectView(R.id.des) TextView des;

    @Override
    protected View initView() {
        View subjectView = View.inflate(GooglePlayApplication.getContext(), R.layout.adapter_subject, null);
        ButterKnife.inject(this, subjectView);
        return subjectView;
    }

    @Override
    public void bindView(Subject data) {
        des.setText(data.getDes());
//        image.setRatio(2.42f);
        ImageLoader.getInstance().displayImage(Url.IMAGE_PREFIX +data.getUrl(),image, ImageLoaderOptions.options);
    }
}
