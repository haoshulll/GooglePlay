package com.myapp.googleplay.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.myapp.googleplay.R;
import com.myapp.googleplay.bean.AppInfo;
import com.myapp.googleplay.http.HttpHelper;
import com.myapp.googleplay.http.Url;
import com.myapp.googleplay.ui.fragment.LoadingPage;
import com.myapp.googleplay.ui.holder.AppDownloadHolder;
import com.myapp.googleplay.ui.holder.AppInfoHolder;
import com.myapp.googleplay.ui.holder.AppIntroHolder;
import com.myapp.googleplay.ui.holder.AppSafeHolder;
import com.myapp.googleplay.ui.holder.AppScreenHolder;
import com.myapp.googleplay.util.CommonUtils;
import com.myapp.googleplay.util.JsonUtil;

import butterknife.ButterKnife;

public class AppDetailActivity extends BaseActivity {
    private String packageName;
    private LoadingPage loadingPage;

    private LinearLayout ll_holder;
    private AppInfo appInfo;
    private ScrollView scrollView;
    private FrameLayout fl_download;

    private AppInfoHolder appInfoHolder;
    private AppSafeHolder appSafeHolder;
    private AppScreenHolder appScreenHolder;
    private AppIntroHolder appIntroHolder;
    private AppDownloadHolder appDownloadHolder;

    @Override
    protected void initView() {
        //1.获取包名
        packageName = getIntent().getStringExtra("packageName");

        //2.由于Activity的界面加载也分为几种不同的state，所以可以用LoadingPage管理
        loadingPage = new LoadingPage(this) {
            @Override
            protected Object loadData() {
                return requestData();
            }

            @Override
            protected View createSuccessView() {
                return getSuccessView();
            }
        };
        setContentView(loadingPage);
    }

    /**
     * 请求数据
     *
     * @return
     */
    private Object requestData() {
        String url = String.format(Url.APP_DETAIL, packageName);
        String json = HttpHelper.get(url);
        appInfo = JsonUtil.parseJsonToBean(json, AppInfo.class);

        if (appInfo != null) {
            CommonUtils.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    refreshUI();
                }
            });
        }

        return appInfo;
    }

    private View getSuccessView() {
        View view = View.inflate(this, R.layout.activity_app_detail, null);
        ll_holder = (LinearLayout) view.findViewById(R.id.ll_holder);
        scrollView  = (ScrollView) view.findViewById(R.id.app_detail_scrollview);
        fl_download = (FrameLayout) view.findViewById(R.id.fl_download);
        //1.添加AppInfoHolder
        appInfoHolder = new AppInfoHolder();
        ll_holder.addView(appInfoHolder.getHolderView());
        //2.添加AppSafeHolder
        appSafeHolder = new AppSafeHolder();
        ll_holder.addView(appSafeHolder.getHolderView());
        //3.添加appScreenHolder
        appScreenHolder = new AppScreenHolder();
        ll_holder.addView(appScreenHolder.getHolderView());
        //4.添加appScreenHolder
        appIntroHolder = new AppIntroHolder();
        ll_holder.addView(appIntroHolder.getHolderView());
        appIntroHolder.setScrollView(scrollView);
        //5.添加appDownloadHolder
        appDownloadHolder = new AppDownloadHolder();
        fl_download.addView(appDownloadHolder.getHolderView());


        return view;
    }


    private void refreshUI() {
        //1.绑定AppINfoHolder的数据
        appInfoHolder.bindView(appInfo);
        //2.绑定AppSafeHolder的数据
        appSafeHolder.bindView(appInfo);
        //3.绑定appScreenHolder的数据
        appScreenHolder.bindView(appInfo);
        //4.绑定appScreenHolder的数据
        appIntroHolder.bindView(appInfo);
        //5.绑定appDownloadHolder的数据
        appDownloadHolder.bindView(appInfo);
    }

    @Override
    protected void initActionBar() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.app_detail));
        //设置Home按钮可以被点击
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

    }


    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
