package com.myapp.googleplay.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by haoshul on 2016/6/26.
 */
public class BasePagerAdapter<T> extends android.support.v4.view.PagerAdapter {
    protected ArrayList<T> list;

    public BasePagerAdapter(ArrayList<T> list){
        super();
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}
