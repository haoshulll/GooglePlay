package com.myapp.googleplay.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.myapp.googleplay.bean.AppInfo;
import com.myapp.googleplay.ui.holder.BaseHolder;
import com.myapp.googleplay.ui.holder.HomeHolder;

import java.util.ArrayList;

/**
 * Created by haoshul on 2016/6/22.
 */
public class HomeAdapter extends BasicAdapter<AppInfo> {
    public HomeAdapter(ArrayList<AppInfo> list) {
        super(list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    @Override
    public BaseHolder<AppInfo> getHolder(int position) {
        return new HomeHolder();
    }
}
