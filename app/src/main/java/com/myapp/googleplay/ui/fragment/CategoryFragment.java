package com.myapp.googleplay.ui.fragment;

import android.view.View;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.myapp.googleplay.R;
import com.myapp.googleplay.bean.Category;
import com.myapp.googleplay.http.HttpHelper;
import com.myapp.googleplay.http.Url;
import com.myapp.googleplay.ui.adapter.CategoryAdapter;
import com.myapp.googleplay.util.CommonUtils;
import com.myapp.googleplay.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haoshul on 2016/6/21.
 */
public class CategoryFragment extends BaseFragment {
    private ArrayList<Object> list = new ArrayList<>();
    private ListView listView;
    @Override
    protected View getSuccessView() {

        listView = (ListView) View.inflate(getActivity(), R.layout.listview,null);

        return listView;
    }

    @Override
    protected Object requestData() {
        String result = HttpHelper.get(Url.CATEGORY);
        ArrayList<Category> categories = (ArrayList<Category>) JsonUtil.parseJsonToList(result,new TypeToken<List<Category>>(){}.getType());
        if (categories != null){
            for (Category category :categories){
                list.add(category.getTitle());
                list.addAll(category.getInfos());
            }
            CommonUtils.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    listView.setAdapter(new CategoryAdapter(list));
                }
            });
        }
        return categories;
    }
}
