package com.myapp.googleplay.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myapp.googleplay.bean.AppInfo;
import com.myapp.googleplay.http.HttpHelper;
import com.myapp.googleplay.http.Url;
import com.myapp.googleplay.ui.activity.AppDetailActivity;
import com.myapp.googleplay.ui.adapter.HomeAdapter;
import com.myapp.googleplay.util.CommonUtils;
import com.myapp.googleplay.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haoshul on 2016/6/21.
 */
public class GameFragment extends BaseRefreshFragment<AppInfo> {

    @Override
    protected BaseAdapter getAdapter() {
        return new HomeAdapter(list);
    }

    @Override
    protected Object requestData() {
        String result = HttpHelper.get(Url.GAME + list.size());
        Gson gson = new Gson();

        ArrayList<AppInfo> appInfos = (ArrayList<AppInfo>) JsonUtil.parseJsonToList(result,new TypeToken<List<AppInfo>>(){}.getType());
        if (appInfos != null) {
            list.addAll(appInfos);
            CommonUtils.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    baseAdapter.notifyDataSetChanged();

                    pullToRefreshListView.onRefreshComplete();
                }
            });
        }


//        Log.d(TAG, "requestData() returned: " + home.toString());
        return result;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Intent intent = new Intent(getActivity(),AppDetailActivity.class);
        intent.putExtra("packageName",list.get(position-1).getPackageName());
        startActivity(intent);
    }
}
