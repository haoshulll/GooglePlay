package com.myapp.googleplay.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myapp.googleplay.http.HttpHelper;
import com.myapp.googleplay.util.LogUtil;

/**
 * Created by haoshul on 2016/6/21.
 */
public abstract class BaseFragment extends Fragment{
    protected static String TAG = HttpHelper.class.getSimpleName();
    protected LoadingPage loadingPage ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (loadingPage == null){
            LogUtil.e(this,this.getClass().getSimpleName()+":创建loadingPage");
            loadingPage = new LoadingPage(getActivity()) {
                @Override
                protected View createSuccessView() {
                    return getSuccessView();
                }

                @Override
                protected Object loadData() {
                    return requestData();
                }
            };
        }
        return loadingPage;
    }

    protected abstract View getSuccessView();

    protected abstract Object requestData();
}
