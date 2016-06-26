package com.myapp.googleplay.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.myapp.googleplay.R;
import com.myapp.googleplay.global.GooglePlayApplication;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by haoshul on 2016/6/23.
 */
public class CategoryTitleHolder extends BaseHolder<Object> {
    @InjectView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected View initView() {
        View view = View.inflate(GooglePlayApplication.getContext(), R.layout.adapter_category_title, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void bindView(Object data) {
        tvTitle.setText((String)data);
    }
}
