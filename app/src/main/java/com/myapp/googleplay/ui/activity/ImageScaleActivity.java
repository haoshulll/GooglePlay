package com.myapp.googleplay.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.myapp.googleplay.R;
import com.myapp.googleplay.ui.adapter.ImageScaleAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ImageScaleActivity extends Activity {

    @InjectView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_scale);
        ButterKnife.inject(this);

        ArrayList<String> urlList = getIntent().getStringArrayListExtra("urlList");
        int currentItem = getIntent().getIntExtra("currentItem", 0);

        viewPager.setAdapter(new ImageScaleAdapter(urlList));
        viewPager.setCurrentItem(currentItem);
    }
}
