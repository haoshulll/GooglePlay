package com.myapp.googleplay.ui.adapter;

import com.myapp.googleplay.bean.Subject;
import com.myapp.googleplay.ui.holder.BaseHolder;
import com.myapp.googleplay.ui.holder.SubjectHolder;

import java.util.ArrayList;

/**
 * Created by haoshul on 2016/6/23.
 */
public class SubjectAdapter extends BasicAdapter<Subject> {

    public SubjectAdapter(ArrayList<Subject> list) {
        super(list);
    }

    @Override
    public BaseHolder<Subject> getHolder(int position) {
        return new SubjectHolder();
    }
}
