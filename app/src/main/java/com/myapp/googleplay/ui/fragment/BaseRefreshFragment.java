package com.myapp.googleplay.ui.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.myapp.googleplay.R;
import com.myapp.googleplay.global.GooglePlayApplication;

import java.util.ArrayList;

/**
 * Created by haoshul on 2016/6/23.
 */
public abstract class BaseRefreshFragment<T> extends BaseFragment implements AdapterView.OnItemClickListener {
    protected ArrayList<T> list = new ArrayList<>();
    protected PullToRefreshListView pullToRefreshListView;
    protected BaseAdapter baseAdapter;
    protected ListView listView;

    @Override
    protected View getSuccessView() {
        pullToRefreshListView = (PullToRefreshListView) View.inflate(GooglePlayApplication.getContext(), R.layout.ptr_listview, null);
        //设置刷新模式
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);//两边都可以刷新
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                loadingPage.loadDataAndRefreshPage();
            }
        });
        //设置adapter

        listView = pullToRefreshListView.getRefreshableView();
        listView.setOnItemClickListener(this);

        setHeaderView();

        baseAdapter = getAdapter();

        listView.setAdapter(baseAdapter);
        listView.setSelector(android.R.color.transparent);


        return pullToRefreshListView;
    }

    protected void setHeaderView() {}

    protected abstract BaseAdapter getAdapter();

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
    }

    @Override
    protected Object requestData() {
        return null;
    }
}
