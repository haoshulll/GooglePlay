package com.myapp.googleplay.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.google.gson.reflect.TypeToken;
import com.myapp.googleplay.bean.Subject;
import com.myapp.googleplay.http.HttpHelper;
import com.myapp.googleplay.http.Url;
import com.myapp.googleplay.ui.activity.AppDetailActivity;
import com.myapp.googleplay.ui.adapter.SubjectAdapter;
import com.myapp.googleplay.util.CommonUtils;
import com.myapp.googleplay.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haoshul on 2016/6/21.
 */
public class SubjectFragment extends BaseRefreshFragment<Subject> {

    @Override
    protected BaseAdapter getAdapter() {
        return new SubjectAdapter(list);
    }


    @Override
    protected Object requestData() {
        String result = HttpHelper.get(Url.SUBJECT + list.size());
        ArrayList<Subject> subjects = (ArrayList<Subject>) JsonUtil.parseJsonToList(result,new TypeToken<List<Subject>>(){}.getType());
        if (subjects != null){
            list.addAll(subjects);

            CommonUtils.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    baseAdapter.notifyDataSetChanged();

                    pullToRefreshListView.onRefreshComplete();
                }
            });
        }
        return list;
    }



}
