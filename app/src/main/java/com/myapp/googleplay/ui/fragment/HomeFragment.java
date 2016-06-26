package com.myapp.googleplay.ui.fragment;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.myapp.googleplay.R;
import com.myapp.googleplay.bean.AppInfo;
import com.myapp.googleplay.bean.Home;
import com.myapp.googleplay.http.HttpHelper;
import com.myapp.googleplay.http.Url;
import com.myapp.googleplay.ui.activity.AppDetailActivity;
import com.myapp.googleplay.ui.adapter.HomeAdapter;
import com.myapp.googleplay.ui.adapter.HomeHeaderAdapter;
import com.myapp.googleplay.util.CommonUtils;
import com.myapp.googleplay.util.JsonUtil;

/**
 * Created by haoshul on 2016/6/21.
 */
public class HomeFragment extends BaseRefreshFragment<AppInfo> {
    private Home home;
    private ViewPager headerVp;

    @Override
    protected void setHeaderView() {
        View headerView = View.inflate(getActivity(), R.layout.layout_home_head, null);
        headerVp = (ViewPager) headerView.findViewById(R.id.header_vp);
        //先计算设备的宽度
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        //通过图片宽高比计算高度
        float height = (width / 2.65f);
        //将height设置给viewpager的高度
        headerVp.getLayoutParams().height = (int) height;
        //让布局生效
        headerVp.requestLayout();
        listView.addHeaderView(headerView);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Intent intent = new Intent(getActivity(),AppDetailActivity.class);
        intent.putExtra("packageName",list.get(position-2).getPackageName());
        startActivity(intent);
    }

    @Override
    protected BaseAdapter getAdapter() {
        return new HomeAdapter(list);
    }


    @Override
    protected Object requestData() {
        String result = HttpHelper.get(Url.HOME + list.size());
        home = JsonUtil.parseJsonToBean(result, Home.class);
        if (home != null) {
            list.addAll(home.getList());
            CommonUtils.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    if (home.getPicture() != null && home.getPicture().size() > 0) {
                        //第一页数据，只有第一页header数据
                        Log.d(TAG, "run() returned: " + home.getPicture().toString());
                        headerVp.setAdapter(new HomeHeaderAdapter(home.getPicture()));
                    }
                    baseAdapter.notifyDataSetChanged();

                    pullToRefreshListView.onRefreshComplete();
                }
            });
        }

//        Log.d(TAG, "requestData() returned: " + home.toString());
        return result;
    }
}
